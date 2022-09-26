package com.xpense.android.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Success
import com.xpense.android.domain.model.Txn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

// TODO:
//  this class is a copy of TransactionRepositoryFake in the local test source set
//  If you'd like to share files between the test and androidTest source set, you can
//  configure,via gradle (https://github.com/android/architecture-samples/blob/f4128dd8dbea5d1aac5d5acd5f346bb82187fbe6/app/build.gradle#L20),
//  a sharedTest folder (https://github.com/android/architecture-samples/tree/reactive/app/src)
//  as seen in the Architecture Blueprints reactive sample (https://github.com/android/architecture-samples/tree/reactive).
class FakeAndroidTxnRepository : TxnRepository {

    var transactionsServiceData: LinkedHashMap<Long, Txn> = LinkedHashMap()

    private val _observableTransactions = MutableLiveData<Result<List<Txn>>>()

    override fun observeTransactionsResult(): LiveData<Result<List<Txn>>> =
        _observableTransactions

    override fun observeTransactionsFlow(): Flow<List<Txn>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransaction(txn: Txn) {
        transactionsServiceData[txn.id] = txn
        _observableTransactions.postValue(Success(transactionsServiceData.values.toList()))
    }

    override suspend fun getTransactionResultById(txnId: Long): Result<Txn> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionsResult(): Result<List<Txn>> =
        Success(transactionsServiceData.values.toList())

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
        _observableTransactions.value = getTransactionsResult()
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }

    fun addTransaction(vararg txns: Txn) {
        for (txn in txns) {
            transactionsServiceData[txn.id] = txn
        }
        runBlocking { refreshTransactions() }
    }
}
