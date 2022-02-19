package com.xpense.android.data.source.remote

import androidx.lifecycle.LiveData
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.Transaction

object TransactionDataSourceRemote : TransactionDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TRANSACTIONS_SERVICE_DATA = LinkedHashMap<String, Transaction>(2)

    override fun observeTransactions(): LiveData<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(transactionId: Long): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactions(): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions() {
        TRANSACTIONS_SERVICE_DATA.clear()
    }
}
