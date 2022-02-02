package com.xpense.android.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction_table")
data class Transaction(
        @PrimaryKey(autoGenerate = true)
        var transactionId: Long = 0L,

        @ColumnInfo(name = "created_timestamp")
        val createdTimestamp: Date? = null,

        @ColumnInfo(name = "amount")
        var amount: Double = 0.0,

        @ColumnInfo(name = "description")
        var description: String = ""
)
