package com.xpense.android.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.xpense.android.data.Transaction

@BindingAdapter("transactionTimestampString")
fun TextView.setTransactionTimestampString(item: Transaction?) {
    item?.let {
        text = getFormattedDateString(item.createdTimestamp)
    }
}
