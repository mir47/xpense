package com.xpense.android.data.source

import com.xpense.android.data.Result
import com.xpense.android.data.Result.Success
import com.xpense.android.data.Result.Error
import com.xpense.android.data.source.local.model.TxnEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTxnDataSource(
    var txnEntities: MutableList<TxnEntity> = mutableListOf()
) : TxnDataSource {

    override fun observeTransactionsResult(): Flow<Result<List<TxnEntity>>> {
        return flowOf(Success(txnEntities))
    }

    override suspend fun saveTransaction(txnEntity: TxnEntity) {
        txnEntities.add(txnEntity)
    }

    override suspend fun getTransactionResultById(txnId: Long): Result<TxnEntity> {
        return txnEntities.find { it.transactionId == txnId }?.let {
            Success(it)
        } ?: Error(Exception("Transaction not found"))
    }

    override suspend fun getTransactionsResult(): Result<List<TxnEntity>> =
        Success(txnEntities)

    override suspend fun getTransactions(): List<TxnEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(txnEntity: TxnEntity) {
        txnEntities.find { it.transactionId == txnEntity.transactionId }?.let {
            txnEntities.remove(it)
            txnEntities.add(txnEntity)
        }
    }

    override suspend fun flagTransaction(txnId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }
}
