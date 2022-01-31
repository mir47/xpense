package com.xpense.android.data.remote

import androidx.lifecycle.LiveData
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.local.Transaction

class TransactionRemoteDataSource : TransactionDataSource {
    override suspend fun observeTransactions(): LiveData<List<Transaction>> {
        TODO("Not yet implemented")
    }
}