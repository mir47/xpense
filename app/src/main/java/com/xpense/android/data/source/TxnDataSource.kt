package com.xpense.android.data.source

import androidx.lifecycle.LiveData
import com.xpense.android.data.Result
import com.xpense.android.data.source.local.model.TxnEntity

interface TxnDataSource {

    fun observeTransactionsResult(): LiveData<Result<List<TxnEntity>>>

    suspend fun saveTransaction(txnEntity: TxnEntity)

    suspend fun getTransactionResultById(txnId: Long): Result<TxnEntity>

    suspend fun getTransactionsResult(): Result<List<TxnEntity>>

    suspend fun getTransactions(): List<TxnEntity>

    suspend fun updateTransaction(txnEntity: TxnEntity)

    suspend fun flagTransaction(txnId: Long, flagged: Boolean)

    suspend fun deleteAllTransactions()
}
