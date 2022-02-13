package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.local.Transaction

interface TransactionRepository {
    fun observeTransactions(): LiveData<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getTransaction(transactionId: Long): Transaction?
    suspend fun updateTransaction(transaction: Transaction)
}
