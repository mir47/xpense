package com.xpense.android.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.xpense.android.data.Result
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.data.TxnDataSource
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.model.toTxn
import com.xpense.android.domain.model.toTxnEntity
import com.xpense.android.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TxnRepositoryImpl constructor(
    private val txnDataSourceLocal: TxnDataSource,
    private val txnDataSourceRemote: TxnDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TxnRepository {

    override fun observeTransactions(): LiveData<Result<List<Txn>>> {
        wrapEspressoIdlingResource {
            return Transformations.map(txnDataSourceLocal.observeTransactions()) {
                Success((it as Success).data.map { txnEntity -> txnEntity.toTxn() })
            }
        }
    }

    override suspend fun saveTransaction(txn: Txn) {
        wrapEspressoIdlingResource {
            txnDataSourceLocal.saveTransaction(txn.toTxnEntity())
            txnDataSourceRemote.saveTransaction(txn.toTxnEntity())
        }
    }

    override suspend fun getTransaction(txnId: Long): Result<Txn> {
        wrapEspressoIdlingResource {
            return Success((txnDataSourceLocal.getTransaction(txnId) as Success).data.toTxn())
        }
    }

    override suspend fun getTransactions(): Result<List<Txn>> {
        wrapEspressoIdlingResource {
            return Success((txnDataSourceLocal.getTransactions() as Success).data.map { it.toTxn() })
        }
    }

    override suspend fun getTxns(): List<Txn> {
        wrapEspressoIdlingResource {
            return txnDataSourceLocal.getTxnsData().map { it.toTxn() }
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
