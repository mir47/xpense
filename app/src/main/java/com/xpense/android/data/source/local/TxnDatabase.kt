package com.xpense.android.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xpense.android.data.source.local.model.TxnEntity

/**
 *
 * exportSchema is true by default. It saves the schema of the db to a folder
 * to provide a version history of the db, that can be helpful for complex
 * databases that change often.
 */
@Database(entities = [TxnEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TxnDatabase : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract fun txnDao(): TxnDao
}
