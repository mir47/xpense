package com.xpense.android.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "transaction_table")
@Parcelize
data class Transaction(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "transaction_id")
        var transactionId: Long = 0L,

        /**
         * Room can't store Date type, so we use a TypeConverter to convert Date to Long.
         * See [Converters.toTimestamp]
         */
        @ColumnInfo(name = "created_timestamp")
        val createdTimestamp: Date? = null,

        @ColumnInfo(name = "amount")
        var amount: Double = 0.0,

        @ColumnInfo(name = "description")
        var description: String = ""
) : Parcelable