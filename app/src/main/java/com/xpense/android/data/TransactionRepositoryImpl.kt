package com.xpense.android.data

import androidx.lifecycle.LiveData

class TransactionRepositoryImpl constructor(
    private val transactionDataSourceLocal: TransactionDataSource,
    private val transactionDataSourceRemote: TransactionDataSource
) : TransactionRepository {

    override fun observeTransactions(): LiveData<List<Transaction>> =
        transactionDataSourceLocal.observeTransactions()

    override suspend fun insertTransaction(transaction: Transaction) =
        transactionDataSourceLocal.insertTransaction(transaction)

    override suspend fun getTransaction(transactionId: Long) =
        transactionDataSourceLocal.getTransaction(transactionId)

    override suspend fun updateTransaction(transaction: Transaction) =
        transactionDataSourceLocal.updateTransaction(transaction)

}
