package com.xpense.android.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.data.source.local.model.TxnEntity
import kotlinx.coroutines.runBlocking

class FakeTxnRepository : TxnRepository {

    var transactionsServiceData: LinkedHashMap<Long, TxnEntity> = LinkedHashMap()

    private val observableTransactions = MutableLiveData<Result<List<TxnEntity>>>()

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun observeTransactions(): LiveData<Result<List<TxnEntity>>> =
        observableTransactions

    override suspend fun saveTransaction(txnEntity: TxnEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(transactionId: Long): Result<TxnEntity> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        transactionsServiceData[transactionId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find transaction"))
    }

    override suspend fun getTransactions(): Result<List<TxnEntity>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        return Success(transactionsServiceData.values.toList())
    }

    override suspend fun updateTransaction(txnEntity: TxnEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransactions() {
        observableTransactions.value = getTransactions()
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }

    fun addTransactions(vararg txnEntities: TxnEntity) {
        for (txn in txnEntities) {
            transactionsServiceData[txn.transactionId] = txn
        }
        runBlocking { refreshTransactions() }
    }
}
