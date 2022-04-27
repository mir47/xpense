package com.xpense.android.data.source.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

// TODO: rename to database object (for database Transaction model) - see dev-bytes project
@Entity(tableName = "txn_table")
@Parcelize
data class TxnEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "transaction_id")
        var transactionId: Long = 0L,

        /**
         * Room can't store Date type, so we use a TypeConverter to convert Date to Long.
         * See [com.xpense.android.data.source.local.Converters.toTimestamp]
         */
        @ColumnInfo(name = "created_timestamp")
        val createdTimestamp: Date? = null,

        @ColumnInfo(name = "amount")
        var amount: Double = 0.0,

        @ColumnInfo(name = "description")
        var description: String = "",

        @ColumnInfo(name = "flagged")
        var flagged: Boolean = false
) : Parcelable
