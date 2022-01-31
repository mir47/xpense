package com.xpense.android

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.TransactionRepository
import com.xpense.android.data.TransactionRepositoryImpl
import com.xpense.android.data.local.TransactionDatabase
import com.xpense.android.data.local.TransactionLocalDataSource
import com.xpense.android.data.remote.TransactionRemoteDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {
    private val lock = Any()

    private var database: TransactionDatabase? = null

    @Volatile
    var transactionRepository: TransactionRepository? = null
//        @VisibleForTesting set

    fun provideTransactionRepository(context: Context): TransactionRepository {
        synchronized(this) {
            return transactionRepository ?: createTransactionRepository(context)
        }
    }

    private fun createTransactionRepository(context: Context): TransactionRepository {
        val newRepo = TransactionRepositoryImpl(
            TransactionRemoteDataSource,
            createTransactionLocalDataSource(context)
        )
        transactionRepository = newRepo
        return newRepo
    }

    private fun createTransactionLocalDataSource(context: Context): TransactionDataSource {
        val database = database ?: createDataBase(context)
        return TransactionLocalDataSource(database.transactionDao())
    }

    private fun createDataBase(context: Context): TransactionDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            TransactionDatabase::class.java, "transaction_database"
        ).build()
        database = result
        return result
    }

//    @VisibleForTesting
//    fun resetRepository() {
//        synchronized(lock) {
//            runBlocking { TransactionRemoteDataSource.deleteAllTasks() }
//            // Clear all data to avoid test pollution.
//            database?.apply {
//                clearAllTables()
//                close()
//            }
//            database = null
//            transactionRepository = null
//        }
//    }
}