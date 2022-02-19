package com.xpense.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import kotlinx.coroutines.runBlocking

class FakeTransactionRepository : TransactionRepository {

    var transactionsServiceData: LinkedHashMap<Long, Transaction> = LinkedHashMap()

    private val observableTransactions = MutableLiveData<Result<List<Transaction>>>()

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun observeTransactions(): LiveData<Result<List<Transaction>>> =
        observableTransactions

    override suspend fun insertTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(transactionId: Long): Result<Transaction> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        transactionsServiceData[transactionId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find transaction"))
    }

    override suspend fun getTransactions(): Result<List<Transaction>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        return Success(transactionsServiceData.values.toList())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransactions() {
        observableTransactions.value = getTransactions()
    }

    fun addTasks(vararg transactions: Transaction) {
        for (transaction in transactions) {
            transactionsServiceData[transaction.transactionId] = transaction
        }
        runBlocking { refreshTransactions() }
    }
}
