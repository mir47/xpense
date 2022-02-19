package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TransactionRepositoryImpl constructor(
    private val transactionDataSourceLocal: TransactionDataSource,
    private val transactionDataSourceRemote: TransactionDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TransactionRepository {

    override fun observeTransactions(): LiveData<Result<List<Transaction>>> =
        transactionDataSourceLocal.observeTransactions()

    override suspend fun insertTransaction(transaction: Transaction) =
        transactionDataSourceLocal.saveTransaction(transaction)

    override suspend fun getTransaction(transactionId: Long) =
        transactionDataSourceLocal.getTransaction(transactionId)

    override suspend fun getTransactions(): Result<List<Transaction>> =
        transactionDataSourceLocal.getTransactions()

    override suspend fun updateTransaction(transaction: Transaction) =
        transactionDataSourceLocal.updateTransaction(transaction)

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransactions() =
        updateTransactionsFromRemoteDataSource()

    private suspend fun updateTransactionsFromRemoteDataSource() {
        val remoteTransactions = transactionDataSourceRemote.getTransactions()

        if (remoteTransactions is Success) {
            // Real apps might want to do a proper sync
            transactionDataSourceLocal.deleteAllTransactions()
            remoteTransactions.data.forEach { transaction ->
                transactionDataSourceLocal.saveTransaction(transaction)
            }
        } else if (remoteTransactions is Error) {
            throw remoteTransactions.exception
        }
    }
}
