package com.xpense.android.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDateString(date: Date?): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return date?.let { format.format(it) }.orEmpty()
}
