package com.xpense.android.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.domain.model.Txn
import kotlinx.coroutines.runBlocking

class FakeTxnRepository : TxnRepository {

    var transactionsServiceData: LinkedHashMap<Long, Txn> = LinkedHashMap()

    private val observableTransactions = MutableLiveData<Result<List<Txn>>>()

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun observeTransactions(): LiveData<Result<List<Txn>>> =
        observableTransactions

    override suspend fun saveTransaction(txnEntity: Txn) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(txnId: Long): Result<Txn> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        transactionsServiceData[txnId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find transaction"))
    }

    override suspend fun getTransactions(): Result<List<Txn>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        return Success(transactionsServiceData.values.toList())
    }

    override suspend fun getTxns(): List<Txn> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(txn: Txn) {
        TODO("Not yet implemented")
    }

    override suspend fun flagTransaction(txnId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransactions() {
        observableTransactions.value = getTransactions()
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }

    fun addTransactions(vararg txnEntities: Txn) {
        for (txn in txnEntities) {
            transactionsServiceData[txn.id] = txn
        }
        runBlocking { refreshTransactions() }
    }
}
