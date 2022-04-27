package com.xpense.android.domain.repository

import androidx.lifecycle.LiveData
import com.xpense.android.data.Result
import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.domain.model.Txn

// TODO: create domain object (for domain Transaction model) - see dev-bytes project
interface TxnRepository {

    fun observeTransactions(): LiveData<Result<List<TxnEntity>>>

    suspend fun saveTransaction(txnEntity: TxnEntity)

    suspend fun getTransaction(transactionId: Long): Result<TxnEntity>

    suspend fun getTransactions(): Result<List<TxnEntity>>

    suspend fun getTxns(): List<Txn>

    suspend fun updateTransaction(txnEntity: TxnEntity)

    suspend fun flagTransaction(transactionId: Long, flagged: Boolean)

    suspend fun refreshTransactions()

    suspend fun deleteAllTransactions()
}
