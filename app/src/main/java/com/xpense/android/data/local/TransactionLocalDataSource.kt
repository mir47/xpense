package com.xpense.android.data.local

import androidx.lifecycle.LiveData
import com.xpense.android.data.TransactionDataSource

class TransactionLocalDataSource internal constructor(
    private val transactionDao: TransactionDao,
): TransactionDataSource {
    override suspend fun observeTransactions(): LiveData<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }
}