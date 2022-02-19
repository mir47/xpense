package com.xpense.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking

// TODO:
//  this class is a copy of TransactionRepositoryFake in the local test source set
//  If you'd like to share files between the test and androidTest source set, you can
//  configure,via gradle (https://github.com/android/architecture-samples/blob/f4128dd8dbea5d1aac5d5acd5f346bb82187fbe6/app/build.gradle#L20),
//  a sharedTest folder (https://github.com/android/architecture-samples/tree/reactive/app/src)
//  as seen in the Architecture Blueprints reactive sample (https://github.com/android/architecture-samples/tree/reactive).
class FakeAndroidTransactionRepository : TransactionRepository {

    var transactionsServiceData: LinkedHashMap<Long, Transaction> = LinkedHashMap()

    private val _observableTransactions = MutableLiveData<List<Transaction>>()

    override fun observeTransactions(): LiveData<List<Transaction>> =
        _observableTransactions

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionsServiceData[transaction.transactionId] = transaction
        _observableTransactions.postValue(transactionsServiceData.values.toList())
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
        _observableTransactions.value = getTransactions()
    }

    fun addTasks(vararg transactions: Transaction) {
        for (transaction in transactions) {
            transactionsServiceData[transaction.transactionId] = transaction
        }
        runBlocking { refreshTasks() }
    }
}
