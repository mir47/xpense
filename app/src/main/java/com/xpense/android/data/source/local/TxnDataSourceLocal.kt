package com.xpense.android.data.source.local

import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.data.asResult
import com.xpense.android.data.source.TxnDataSource
import com.xpense.android.data.source.local.model.TxnEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TxnDataSourceLocal internal constructor(
    private val txnDao: TxnDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): TxnDataSource {
    override fun observeTransactionsResult(): Flow<Result<List<TxnEntity>>> =
        txnDao.observeTransactions().asResult()

    override suspend fun saveTransaction(txnEntity: TxnEntity) =
        txnDao.insert(txnEntity)

    override suspend fun getTransactionResultById(txnId: Long) =
        txnDao.getTransactionWithId(txnId)?.let { Success(it) }
            ?: Error(Exception("Error"))

    override suspend fun getTransactionsResult(): Result<List<TxnEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactions(): List<TxnEntity> =
        txnDao.getAllTransactions()

    override suspend fun updateTransaction(txnEntity: TxnEntity) =
        txnDao.update(txnEntity)

    override suspend fun flagTransaction(txnId: Long, flagged: Boolean) =
        txnDao.updateFlagged(txnId, flagged)

    override suspend fun deleteAllTransactions() =
        txnDao.clear()
}
