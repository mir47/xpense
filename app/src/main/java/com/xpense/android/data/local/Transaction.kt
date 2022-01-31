package com.xpense.android.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
        @PrimaryKey(autoGenerate = true)
        var transactionId: Long = 0L,

        @ColumnInfo(name = "time_milli")
        val timeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "amount")
        var amount: Double = 0.0
)
