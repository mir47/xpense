package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.source.local.model.TxnEntity

// TODO: consider moving to data.source package
interface TxnDataSource {

    fun observeTransactions(): LiveData<Result<List<TxnEntity>>>

    suspend fun saveTransaction(txnEntity: TxnEntity)

    suspend fun getTransaction(transactionId: Long): Result<TxnEntity>

    suspend fun getTransactions(): Result<List<TxnEntity>>

    suspend fun getTxnsData(): List<TxnEntity>

    suspend fun updateTransaction(txnEntity: TxnEntity)

    suspend fun flagTransaction(transactionId: Long, flagged: Boolean)

    suspend fun deleteAllTransactions()
}
