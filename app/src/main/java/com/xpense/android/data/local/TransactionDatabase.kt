package com.xpense.android.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 *
 * exportSchema is true by default. It saves the schema of the db to a folder
 * to provide a version history of the db, that can be helpful for complex
 * databases that change often.
 */
@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TransactionDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract fun transactionDao(): TransactionDao

    companion object {

        /**
         * Static method that creates an instance of [TransactionDatabase] and returns the DAO
         */
        fun createTransactionDao(context: Context): TransactionDao {
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
}