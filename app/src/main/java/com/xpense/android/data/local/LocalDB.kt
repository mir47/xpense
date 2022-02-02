package com.xpense.android.data.local

import android.content.Context
import androidx.room.Room

/**
 * Singleton class that is used to create a [TransactionDatabase]
 */
object LocalDB {

    /**
     * static method that creates a class and returns the DAO
     */
    fun createTransactionDao(context: Context): TransactionDao {
        return Room.databaseBuilder(
            context.applicationContext,
            TransactionDatabase::class.java, "transaction_database"
        ).build().transactionDao()
    }
}