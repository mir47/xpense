package com.xpense.android.domain.repository

import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.domain.model.Txn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class FakeTxnRepository : TxnRepository {

    var transactionsServiceData: LinkedHashMap<Long, Txn> = LinkedHashMap()

    private val observableTransactions = MutableLiveData<Result<List<Txn>>>()

    var shouldReturnError = false

    override fun observeTransactionsResult(): Flow<Result<List<Txn>>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransaction(txn: Txn) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionResultById(txnId: Long): Result<Txn> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        transactionsServiceData[txnId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find transaction"))
    }

    override suspend fun getTransactionsResult(): Result<List<Txn>> {
        return if (shouldReturnError) {
            Error(Exception("Test exception"))
        } else {
            Success(transactionsServiceData.values.toList())
        }
    }

    override suspend fun getTransactions(): List<Txn> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(txn: Txn) {
        TODO("Not yet implemented")
    }

    override suspend fun flagTransaction(txnId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransactions() {
        observableTransactions.value = getTransactionsResult()
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
