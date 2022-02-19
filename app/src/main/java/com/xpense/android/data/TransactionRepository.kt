package com.xpense.android.data

import androidx.lifecycle.LiveData

interface TransactionRepository {
    fun observeTransactions(): LiveData<Result<List<Transaction>>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getTransaction(transactionId: Long): Result<Transaction>
    suspend fun getTransactions(): Result<List<Transaction>>
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun refreshTransactions()
}
