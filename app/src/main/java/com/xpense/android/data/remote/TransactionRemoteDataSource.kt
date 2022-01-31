package com.xpense.android.data.remote

import com.xpense.android.data.TransactionDataSource
import com.xpense.android.db.Transaction

class TransactionRemoteDataSource : TransactionDataSource {
    override suspend fun observeTransactions(): List<Transaction> {
        TODO("Not yet implemented")
    }
}