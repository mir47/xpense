package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.local.Transaction

class TransactionRepositoryImpl constructor(
    private val transactionLocalDataSource: TransactionDataSource,
    private val transactionRemoteDataSource: TransactionDataSource
) : TransactionRepository {

    override suspend fun observeTransactions(): LiveData<List<Transaction>> =
        transactionLocalDataSource.observeTransactions()

}