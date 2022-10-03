package com.xpense.android.data.source

import com.xpense.android.data.Result
import com.xpense.android.data.source.local.model.TxnEntity
import kotlinx.coroutines.flow.Flow

interface TxnDataSource {

    fun observeTransactionsResult(): Flow<Result<List<TxnEntity>>>

    suspend fun saveTransaction(txnEntity: TxnEntity)

    suspend fun getTransactionResultById(txnId: Long): Result<TxnEntity>

    suspend fun getTransactionsResult(): Result<List<TxnEntity>>

    suspend fun getTransactions(): List<TxnEntity>

    suspend fun updateTransaction(txnEntity: TxnEntity)

    suspend fun flagTransaction(txnId: Long, flagged: Boolean)

    suspend fun deleteAllTransactions()
}
