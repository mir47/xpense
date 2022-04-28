package com.xpense.android.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.xpense.android.domain.model.Txn

@BindingAdapter("timestampString")
fun TextView.setTimestampString(item: Txn?) {
    item?.let {
        text = getFormattedDateString(item.createdTimestamp)
    }
}
