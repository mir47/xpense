package com.xpense.android.data.local

import com.xpense.android.data.TransactionDataSource
import com.xpense.android.db.Transaction

class TransactionLocalDataSource : TransactionDataSource {
    override suspend fun observeTransactions(): List<Transaction> {
        TODO("Not yet implemented")
    }
}