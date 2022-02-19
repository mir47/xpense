package com.xpense.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking

// TODO:
//  consider using a shared class for fakes, because this class is
//  copied from TransactionRepositoryFake in the local test source set
class AndroidTransactionRepositoryFake : TransactionRepository {

    var transactionsServiceData: LinkedHashMap<Long, Transaction> = LinkedHashMap()

    private val observableTransactions = MutableLiveData<List<Transaction>>()

    override fun observeTransactions(): LiveData<List<Transaction>> =
        observableTransactions

    override suspend fun insertTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(transactionId: Long): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactions(): List<Transaction> =
        transactionsServiceData.values.toList()

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTasks() {
        observableTransactions.value = getTransactions()
    }

    fun addTasks(vararg transactions: Transaction) {
        for (transaction in transactions) {
            transactionsServiceData[transaction.transactionId] = transaction
        }
        runBlocking { refreshTasks() }
    }
}
