package com.xpense.android.data.source.remote

import androidx.lifecycle.LiveData
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.Transaction
import kotlinx.coroutines.delay

object TransactionDataSourceRemote : TransactionDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TRANSACTIONS_SERVICE_DATA = LinkedHashMap<String, Transaction>(2)

    override fun observeTransactions(): LiveData<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(transactionId: Long): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactions(): List<Transaction> {
        // Simulate network by delaying the execution
        val transactions = TRANSACTIONS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return transactions
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions() {
        TRANSACTIONS_SERVICE_DATA.clear()
    }
}
