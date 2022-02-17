package com.xpense.android.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TransactionRepositoryImpl constructor(
    private val transactionDataSourceLocal: TransactionDataSource,
    private val transactionDataSourceRemote: TransactionDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TransactionRepository {

    override fun observeTransactions(): LiveData<List<Transaction>> =
        transactionDataSourceLocal.observeTransactions()

    override suspend fun insertTransaction(transaction: Transaction) =
        transactionDataSourceLocal.insertTransaction(transaction)

    override suspend fun getTransaction(transactionId: Long) =
        transactionDataSourceLocal.getTransaction(transactionId)

    override suspend fun getTransactions(): List<Transaction> =
        transactionDataSourceLocal.getTransactions()

    override suspend fun updateTransaction(transaction: Transaction) =
        transactionDataSourceLocal.updateTransaction(transaction)

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }

}
