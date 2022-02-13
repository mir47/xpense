package com.xpense.android.data.source.local

import androidx.lifecycle.LiveData
import com.xpense.android.data.Transaction
import com.xpense.android.data.TransactionDataSource

class TransactionDataSourceLocal internal constructor(
    private val transactionDao: TransactionDao,
): TransactionDataSource {
    override fun observeTransactions(): LiveData<List<Transaction>> =
        transactionDao.getAllTransactions()

    override suspend fun insertTransaction(transaction: Transaction) =
        transactionDao.insert(transaction)

    override suspend fun getTransaction(transactionId: Long): Transaction? =
        transactionDao.getTransactionWithId(transactionId)

    override suspend fun updateTransaction(transaction: Transaction) =
        transactionDao.update(transaction)
}
