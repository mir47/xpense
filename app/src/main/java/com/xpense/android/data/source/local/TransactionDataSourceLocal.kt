package com.xpense.android.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.data.Transaction
import com.xpense.android.data.TransactionDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TransactionDataSourceLocal internal constructor(
    private val transactionDao: TransactionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): TransactionDataSource {
    override fun observeTransactions(): LiveData<Result<List<Transaction>>> =
        transactionDao.observeTransactions().map {
            Success(it)
        }

    override suspend fun saveTransaction(transaction: Transaction) =
        transactionDao.insert(transaction)

    override suspend fun getTransaction(transactionId: Long) =
        transactionDao.getTransactionWithId(transactionId)?.let { Success(it) }
            ?: Error(Exception("Error"))

    override suspend fun getTransactions(): Result<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(transaction: Transaction) =
        transactionDao.update(transaction)

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) =
        transactionDao.updateFlagged(transactionId, flagged)

    override suspend fun deleteAllTransactions() =
        transactionDao.clear()
}
