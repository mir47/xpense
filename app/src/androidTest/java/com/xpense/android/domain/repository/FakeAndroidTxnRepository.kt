package com.xpense.android.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Success
import com.xpense.android.data.source.local.model.TxnEntity
import kotlinx.coroutines.runBlocking

// TODO:
//  this class is a copy of TransactionRepositoryFake in the local test source set
//  If you'd like to share files between the test and androidTest source set, you can
//  configure,via gradle (https://github.com/android/architecture-samples/blob/f4128dd8dbea5d1aac5d5acd5f346bb82187fbe6/app/build.gradle#L20),
//  a sharedTest folder (https://github.com/android/architecture-samples/tree/reactive/app/src)
//  as seen in the Architecture Blueprints reactive sample (https://github.com/android/architecture-samples/tree/reactive).
class FakeAndroidTxnRepository : TxnRepository {

    var transactionsServiceData: LinkedHashMap<Long, TxnEntity> = LinkedHashMap()

    private val _observableTransactions = MutableLiveData<Result<List<TxnEntity>>>()

    override fun observeTransactions(): LiveData<Result<List<TxnEntity>>> =
        _observableTransactions

    override suspend fun saveTransaction(txnEntity: TxnEntity) {
        transactionsServiceData[txnEntity.transactionId] = txnEntity
        _observableTransactions.postValue(Success(transactionsServiceData.values.toList()))
    }

    override suspend fun getTransaction(transactionId: Long): Result<TxnEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactions(): Result<List<TxnEntity>> =
        Success(transactionsServiceData.values.toList())

    override suspend fun updateTransaction(txnEntity: TxnEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransactions() {
        _observableTransactions.value = getTransactions()
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }

    fun addTasks(vararg txnEntities: TxnEntity) {
        for (transaction in txnEntities) {
            transactionsServiceData[transaction.transactionId] = transaction
        }
        runBlocking { refreshTransactions() }
    }
}
