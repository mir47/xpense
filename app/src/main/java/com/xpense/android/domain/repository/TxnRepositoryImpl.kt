package com.xpense.android.domain.repository

import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.data.source.TxnDataSource
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.model.toTxn
import com.xpense.android.domain.model.toTxnEntity
import com.xpense.android.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class TxnRepositoryImpl @Inject constructor(
    private val txnDataSourceLocal: TxnDataSource,
    private val txnDataSourceRemote: TxnDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TxnRepository {

    override fun observeTransactionsResult(): Flow<Result<List<Txn>>> {
        wrapEspressoIdlingResource {
            return txnDataSourceLocal.observeTransactionsResult().transform {
                if (it is Success) {
                    // need to `emit` inside `transform` - but not sure why?
                    emit(Success(it.data.map { txnEntity -> txnEntity.toTxn() }))
                }
            }
        }
    }

    override suspend fun saveTransaction(txn: Txn) {
        wrapEspressoIdlingResource {
            txnDataSourceLocal.saveTransaction(txn.toTxnEntity())
            txnDataSourceRemote.saveTransaction(txn.toTxnEntity())
        }
    }

    override suspend fun getTransactionResultById(txnId: Long): Result<Txn> {
        wrapEspressoIdlingResource {
            return Success((txnDataSourceLocal.getTransactionResultById(txnId) as Success).data.toTxn())
        }
    }

    override suspend fun getTransactionsResult(): Result<List<Txn>> {
        wrapEspressoIdlingResource {
            return Success((txnDataSourceLocal.getTransactionsResult() as Success).data.map { it.toTxn() })
        }
    }

    override suspend fun getTransactions(): List<Txn> {
        wrapEspressoIdlingResource {
            return txnDataSourceLocal.getTransactions().map { it.toTxn() }
        }
    }

    override suspend fun updateTransaction(txn: Txn) {
        wrapEspressoIdlingResource {
            txnDataSourceLocal.updateTransaction(txn.toTxnEntity())
        }
    }

    override suspend fun flagTransaction(txnId: Long, flagged: Boolean) {
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
            val remoteTransactions = txnDataSourceRemote.getTransactionsResult()

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
