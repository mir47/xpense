package com.xpense.android.data

import com.xpense.android.db.Transaction

class TransactionRepositoryImpl constructor(
    private val transactionLocalDataSource: TransactionDataSource,
    private val transactionRemoteDataSource: TransactionDataSource
) : TransactionRepository {

    override suspend fun observeTransactions(forceUpdate: Boolean): List<Transaction> {
        TODO("Not yet implemented")
    }
}