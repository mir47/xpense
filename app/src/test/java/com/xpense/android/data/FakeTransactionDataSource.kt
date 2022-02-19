package com.xpense.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeTransactionDataSource(
    var transactions: MutableList<Transaction>? = mutableListOf()
) : TransactionDataSource {
    override fun observeTransactions(): LiveData<List<Transaction>> {
        return MutableLiveData(transactions)
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        transactions?.add(transaction)
    }

    override suspend fun getTransaction(transactionId: Long): Transaction? {
        return transactions?.find { it.transactionId == transactionId }
    }

    override suspend fun getTransactions(): List<Transaction> {
        return transactions ?: emptyList()
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactions?.find { it.transactionId == transaction.transactionId }?.let {
            transactions?.remove(it)
            transactions?.add(transaction)
        }
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }
}
