package com.xpense.android

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.xpense.android.data.TxnDataSource
import com.xpense.android.data.TransactionRepository
import com.xpense.android.data.TransactionRepositoryImpl
import com.xpense.android.data.source.local.TxnDataSourceLocal
import com.xpense.android.data.source.local.TxnDatabase
import com.xpense.android.data.source.remote.TxnDataSourceRemote
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private val lock = Any()

    private var db: TxnDatabase? = null

    @Volatile
    var repository: TransactionRepository? = null
        @VisibleForTesting set

    fun provideTransactionRepository(context: Context): TransactionRepository {
        synchronized(this) {
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): TransactionRepository {
        val newRepo = TransactionRepositoryImpl(
            createLocalDataSource(context),
            TxnDataSourceRemote
        )
        repository = newRepo
        return newRepo
    }

    private fun createLocalDataSource(context: Context): TxnDataSource {
        val db = db ?: createDataBase(context)
        return TxnDataSourceLocal(db.txnDao())
    }

    private fun createDataBase(context: Context): TxnDatabase {
        val roomDb = Room.databaseBuilder(
            context.applicationContext,
            TxnDatabase::class.java,
            "transaction_database"
        )
            // migration strategy - use destructive to recreate a new db
            .fallbackToDestructiveMigration()
            .build()
        db = roomDb
        return roomDb
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                TxnDataSourceRemote.deleteAllTransactions()
            }
            // Clear all data to avoid test pollution.
            db?.apply {
                clearAllTables()
                close()
            }
            db = null
            repository = null
        }
    }
}
