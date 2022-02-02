package com.xpense.android.data.local

import androidx.lifecycle.LiveData
import com.xpense.android.data.TransactionDataSource

class TransactionDataSourceLocal internal constructor(
    private val transactionDao: TransactionDao,
): TransactionDataSource {
    override fun observeTransactions(): LiveData<List<Transaction>> =
        transactionDao.getAllTransactions()

    override suspend fun insertTransaction(transaction: Transaction) =
        transactionDao.insert(transaction)
}