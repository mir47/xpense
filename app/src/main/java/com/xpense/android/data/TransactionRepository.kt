package com.xpense.android.data

import androidx.lifecycle.LiveData

// TODO: create domain object (for domain Transaction model) - see dev-bytes project
interface TransactionRepository {

    fun observeTransactions(): LiveData<Result<List<TxnEntity>>>

    suspend fun saveTransaction(txnEntity: TxnEntity)

    suspend fun getTransaction(transactionId: Long): Result<TxnEntity>

    suspend fun getTransactions(): Result<List<TxnEntity>>

    suspend fun updateTransaction(txnEntity: TxnEntity)

    suspend fun flagTransaction(transactionId: Long, flagged: Boolean)

    suspend fun refreshTransactions()

    suspend fun deleteAllTransactions()
}
