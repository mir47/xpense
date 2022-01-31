package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.local.Transaction

class TransactionRepositoryImpl constructor(
    private val transactionLocalDataSource: TransactionDataSource,
    private val transactionRemoteDataSource: TransactionDataSource
) : TransactionRepository {

    override fun observeTransactions(): LiveData<List<Transaction>> =
        transactionLocalDataSource.observeTransactions()

    override suspend fun insertTransaction(transaction: Transaction) =
        transactionLocalDataSource.insertTransaction(transaction)

}