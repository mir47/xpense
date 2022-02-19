package com.xpense.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.data.Result.Success
import com.xpense.android.data.Result.Error

class FakeTransactionDataSource(
    var transactions: MutableList<Transaction> = mutableListOf()
) : TransactionDataSource {

    override fun observeTransactions(): LiveData<Result<List<Transaction>>> {
        return MutableLiveData(Success(transactions))
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    override suspend fun getTransaction(transactionId: Long): Result<Transaction> {
        return transactions.find { it.transactionId == transactionId }?.let {
            Success(it)
        } ?: Error(Exception("Transaction not found"))
    }

    override suspend fun getTransactions(): Result<List<Transaction>> =
        Success(transactions)

    override suspend fun updateTransaction(transaction: Transaction) {
        transactions.find { it.transactionId == transaction.transactionId }?.let {
            transactions.remove(it)
            transactions.add(transaction)
        }
    }

    override suspend fun flagTransaction(transactionId: Long, flagged: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions() {
        TODO("Not yet implemented")
    }
}
