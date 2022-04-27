package com.xpense.android.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.data.TxnEntity
import com.xpense.android.data.TxnDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TxnDataSourceLocal internal constructor(
    private val txnDao: TxnDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): TxnDataSource {
    override fun observeTransactions(): LiveData<Result<List<TxnEntity>>> =
        txnDao.observeTransactions().map {
            Success(it)
        }

    override suspend fun saveTransaction(txnEntity: TxnEntity) =
        txnDao.insert(txnEntity)

    override suspend fun getTransaction(transactionId: Long) =
        txnDao.getTransactionWithId(transactionId)?.let { Success(it) }
            ?: Error(Exception("Error"))

    override suspend fun getTransactions(): Result<List<TxnEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(txnEntity: TxnEntity) =
        txnDao.update(txnEntity)

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) =
        txnDao.updateFlagged(transactionId, flagged)

    override suspend fun deleteAllTransactions() =
        txnDao.clear()
}
