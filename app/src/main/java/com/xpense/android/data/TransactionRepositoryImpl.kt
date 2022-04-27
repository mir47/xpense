package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TransactionRepositoryImpl constructor(
    private val transactionDataSourceLocal: TransactionDataSource,
    private val transactionDataSourceRemote: TransactionDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TransactionRepository {

    override fun observeTransactions(): LiveData<Result<List<TxnEntity>>> {
        wrapEspressoIdlingResource {
            return transactionDataSourceLocal.observeTransactions()
        }
    }

    override suspend fun saveTransaction(txnEntity: TxnEntity) {
        wrapEspressoIdlingResource {
            transactionDataSourceLocal.saveTransaction(txnEntity)
            transactionDataSourceRemote.saveTransaction(txnEntity)
        }
    }

    override suspend fun getTransaction(transactionId: Long): Result<TxnEntity> {
        wrapEspressoIdlingResource {
            return transactionDataSourceLocal.getTransaction(transactionId)
        }
    }

    override suspend fun getTransactions(): Result<List<TxnEntity>> {
        wrapEspressoIdlingResource {
            return transactionDataSourceLocal.getTransactions()
        }
    }

    override suspend fun updateTransaction(txnEntity: TxnEntity) {
        wrapEspressoIdlingResource {
            transactionDataSourceLocal.updateTransaction(txnEntity)
        }
    }

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) {
        wrapEspressoIdlingResource {
            TODO("Not yet implemented")
        }
    }

    override suspend fun refreshTransactions() {
        wrapEspressoIdlingResource {
            updateTransactionsFromRemoteDataSource()
        }
    }

    override suspend fun deleteAllTransactions() {
        wrapEspressoIdlingResource {
            transactionDataSourceLocal.deleteAllTransactions()
        }
    }

    private suspend fun updateTransactionsFromRemoteDataSource() {
        wrapEspressoIdlingResource {
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
}
