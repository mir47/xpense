package com.xpense.android.data.remote

import androidx.lifecycle.LiveData
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.local.Transaction

object TransactionDataSourceRemote : TransactionDataSource {
    override fun observeTransactions(): LiveData<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(transactionId: Long): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }
}
