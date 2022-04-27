package com.xpense.android.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.xpense.android.data.TxnEntity

@BindingAdapter("timestampString")
fun TextView.setTimestampString(item: TxnEntity?) {
    item?.let {
        text = getFormattedDateString(item.createdTimestamp)
    }
}
