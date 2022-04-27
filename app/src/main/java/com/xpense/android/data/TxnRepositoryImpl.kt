package com.xpense.android.data

import androidx.lifecycle.LiveData
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TxnRepositoryImpl constructor(
    private val txnDataSourceLocal: TxnDataSource,
    private val txnDataSourceRemote: TxnDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TxnRepository {

    override fun observeTransactions(): LiveData<Result<List<TxnEntity>>> {
        wrapEspressoIdlingResource {
            return txnDataSourceLocal.observeTransactions()
        }
    }

    override suspend fun saveTransaction(txnEntity: TxnEntity) {
        wrapEspressoIdlingResource {
            txnDataSourceLocal.saveTransaction(txnEntity)
            txnDataSourceRemote.saveTransaction(txnEntity)
        }
    }

    override suspend fun getTransaction(transactionId: Long): Result<TxnEntity> {
        wrapEspressoIdlingResource {
            return txnDataSourceLocal.getTransaction(transactionId)
        }
    }

    override suspend fun getTransactions(): Result<List<TxnEntity>> {
        wrapEspressoIdlingResource {
            return txnDataSourceLocal.getTransactions()
        }
    }

    override suspend fun updateTransaction(txnEntity: TxnEntity) {
        wrapEspressoIdlingResource {
            txnDataSourceLocal.updateTransaction(txnEntity)
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
            txnDataSourceLocal.deleteAllTransactions()
        }
    }

    private suspend fun updateTransactionsFromRemoteDataSource() {
        wrapEspressoIdlingResource {
            val remoteTransactions = txnDataSourceRemote.getTransactions()

            if (remoteTransactions is Success) {
                // Real apps might want to do a proper sync
                txnDataSourceLocal.deleteAllTransactions()
                remoteTransactions.data.forEach { transaction ->
                    txnDataSourceLocal.saveTransaction(transaction)
                }
            } else if (remoteTransactions is Error) {
                throw remoteTransactions.exception
            }
        }
    }
}
