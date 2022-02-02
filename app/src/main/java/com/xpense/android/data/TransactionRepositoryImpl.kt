package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.local.Transaction

class TransactionRepositoryImpl constructor(
    private val transactionDataSourceLocal: TransactionDataSource,
    private val transactionDataSourceRemote: TransactionDataSource
) : TransactionRepository {

    override fun observeTransactions(): LiveData<List<Transaction>> =
        transactionDataSourceLocal.observeTransactions()

    override suspend fun insertTransaction(transaction: Transaction) =
        transactionDataSourceLocal.insertTransaction(transaction)

}