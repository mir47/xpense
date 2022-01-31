package com.xpense.android.ui.main

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.xpense.android.data.local.Transaction

@BindingAdapter("transactionAmountString")
fun TextView.setTransactionAmountString(item: Transaction?) {
    item?.let {
        text = item.amount.toString()
    }
}
