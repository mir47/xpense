package com.xpense.android.data

import androidx.lifecycle.LiveData

interface TransactionRepository {
    fun observeTransactions(): LiveData<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getTransaction(transactionId: Long): Transaction?
    suspend fun updateTransaction(transaction: Transaction)
}
