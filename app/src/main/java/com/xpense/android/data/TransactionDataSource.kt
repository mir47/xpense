package com.xpense.android.data

import com.xpense.android.db.Transaction

interface TransactionDataSource {
    suspend fun observeTransactions(): List<Transaction>
}