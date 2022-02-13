package com.xpense.android.data.source.local

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return when (timestamp) {
            null -> null
            else -> Date(timestamp)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
