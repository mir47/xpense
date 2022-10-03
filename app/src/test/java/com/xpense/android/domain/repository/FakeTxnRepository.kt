package com.xpense.android.domain.repository

import com.xpense.android.data.Result
import com.xpense.android.domain.model.Txn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class FakeTxnRepository : TxnRepository {

    var transactionsServiceData: LinkedHashMap<Long, Txn> = LinkedHashMap()

    private val observableTransactions: Flow<Result<List<Txn>>> = flow {
        emit(Result.Loading)
        delay(300)
        emit(getTransactionsResult())
    }

    var shouldReturnError = false

    override fun observeTransactionsResult(): Flow<Result<List<Txn>>> =
        observableTransactions

    override suspend fun saveTransaction(txn: Txn) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionResultById(txnId: Long): Result<Txn> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        transactionsServiceData[txnId]?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Could not find transaction"))
    }

    override suspend fun getTransactionsResult(): Result<List<Txn>> {
        return if (shouldReturnError) {
            Result.Error(Exception("Test exception"))
        } else {
            Result.Success(transactionsServiceData.values.toList())
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
//        observableTransactions = flow { getTransactionsResult() }
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
