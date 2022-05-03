package com.xpense.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result.Success
import com.xpense.android.data.Result.Error
import com.xpense.android.data.source.TxnDataSource
import com.xpense.android.data.source.local.model.TxnEntity

class FakeTxnDataSource(
    var txnEntities: MutableList<TxnEntity> = mutableListOf()
) : TxnDataSource {

    override fun observeTransactionsResult(): LiveData<Result<List<TxnEntity>>> {
        return MutableLiveData(Success(txnEntities))
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
