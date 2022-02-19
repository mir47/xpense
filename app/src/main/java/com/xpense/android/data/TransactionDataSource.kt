package com.xpense.android.data

import androidx.lifecycle.LiveData

interface TransactionDataSource {
    fun observeTransactions(): LiveData<List<Transaction>>
    suspend fun saveTransaction(transaction: Transaction)
    suspend fun getTransaction(transactionId: Long): Transaction?
    suspend fun getTransactions(): List<Transaction>
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteAllTransactions()
}
