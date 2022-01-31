package com.xpense.android.data

import com.xpense.android.db.Transaction

interface TransactionRepository {
    suspend fun observeTransactions(forceUpdate: Boolean = false): List<Transaction>
}