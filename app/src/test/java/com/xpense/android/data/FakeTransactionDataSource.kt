package com.xpense.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result.Success
import com.xpense.android.data.Result.Error

class FakeTransactionDataSource(
    var txnEntities: MutableList<TxnEntity> = mutableListOf()
) : TransactionDataSource {

    override fun observeTransactions(): LiveData<Result<List<TxnEntity>>> {
        return MutableLiveData(Success(txnEntities))
    }

    override suspend fun saveTransaction(txnEntity: TxnEntity) {
        txnEntities.add(txnEntity)
    }

    override suspend fun getTransaction(transactionId: Long): Result<TxnEntity> {
        return txnEntities.find { it.transactionId == transactionId }?.let {
            Success(it)
        } ?: Error(Exception("Transaction not found"))
    }

    override suspend fun getTransactions(): Result<List<TxnEntity>> =
        Success(txnEntities)

    override suspend fun updateTransaction(txnEntity: TxnEntity) {
        txnEntities.find { it.transactionId == txnEntity.transactionId }?.let {
            txnEntities.remove(it)
            txnEntities.add(txnEntity)
        }
    }

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }
}
