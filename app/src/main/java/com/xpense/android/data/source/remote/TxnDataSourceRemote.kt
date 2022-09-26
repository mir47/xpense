package com.xpense.android.data.source.remote

import androidx.lifecycle.LiveData
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Success
import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.data.source.TxnDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

// TODO: create data transfer object (for remote Transaction model) - see dev-bytes project
object TxnDataSourceRemote : TxnDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 500L

    private var TRANSACTIONS_SERVICE_DATA = LinkedHashMap<Long, TxnEntity>(2)

    override fun observeTransactionsResult(): LiveData<Result<List<TxnEntity>>> {
        TODO("Not yet implemented")
    }

    override fun observeTransactionsFlow(): Flow<List<TxnEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransaction(txnEntity: TxnEntity) {
        delay(SERVICE_LATENCY_IN_MILLIS)
        TRANSACTIONS_SERVICE_DATA[txnEntity.transactionId] = txnEntity
    }

    override suspend fun getTransactionResultById(txnId: Long): Result<TxnEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionsResult(): Result<List<TxnEntity>> {
        // Simulate network by delaying the execution
        val transactions = TRANSACTIONS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Success(transactions)
    }

    override suspend fun getTransactions(): List<TxnEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(txnEntity: TxnEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun flagTransaction(txnId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions() {
        TRANSACTIONS_SERVICE_DATA.clear()
    }
}
