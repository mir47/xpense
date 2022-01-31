package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.local.Transaction

interface TransactionDataSource {

    fun observeTransactions(): LiveData<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)
}