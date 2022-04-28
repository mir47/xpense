package com.xpense.android.domain.repository

import androidx.lifecycle.LiveData
import com.xpense.android.data.Result
import com.xpense.android.domain.model.Txn

interface TxnRepository {

    fun observeTransactions(): LiveData<Result<List<Txn>>>

    suspend fun saveTransaction(txn: Txn)

    suspend fun getTransaction(txnId: Long): Result<Txn>

    suspend fun getTransactions(): Result<List<Txn>>

    suspend fun getTxns(): List<Txn>

    suspend fun updateTransaction(txn: Txn)

    suspend fun flagTransaction(txnId: Long, flagged: Boolean)

    suspend fun refreshTransactions()

    suspend fun deleteAllTransactions()
}
