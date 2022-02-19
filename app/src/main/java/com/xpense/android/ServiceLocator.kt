package com.xpense.android

import android.content.Context
import androidx.room.Room
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.TransactionRepository
import com.xpense.android.data.TransactionRepositoryImpl
import com.xpense.android.data.source.local.TransactionDao
import com.xpense.android.data.source.local.TransactionDataSourceLocal
import com.xpense.android.data.source.local.TransactionDatabase
import com.xpense.android.data.source.remote.TransactionDataSourceRemote

object ServiceLocator {
    private var transactionDao: TransactionDao? = null

    @Volatile
    var transactionRepository: TransactionRepository? = null

    fun provideTransactionRepository(context: Context): TransactionRepository {
        synchronized(this) {
            return transactionRepository ?: createTransactionRepository(context)
        }
    }

    private fun createTransactionRepository(context: Context): TransactionRepository {
        val newRepo = TransactionRepositoryImpl(
            createTransactionLocalDataSource(context),
            TransactionDataSourceRemote
        )
        transactionRepository = newRepo
        return newRepo
    }

    private fun createTransactionLocalDataSource(context: Context): TransactionDataSource {
        val dao = transactionDao ?: createTransactionDao(context)
        return TransactionDataSourceLocal(dao)
    }

    private fun createTransactionDao(context: Context): TransactionDao {
        return Room.databaseBuilder(
            context.applicationContext,
            TransactionDatabase::class.java,
            "transaction_database"
        )
            // migration strategy - use destructive to recreate a new db
            .fallbackToDestructiveMigration()
            .build().transactionDao()
    }
}
