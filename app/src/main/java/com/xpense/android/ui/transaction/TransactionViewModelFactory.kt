package com.xpense.android.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xpense.android.db.TransactionDatabaseDao

/**
 * This is boiler plate code for a ViewModel Factory.
 *
 * Provides the TransactionDatabaseDao to the ViewModel.
 */
class TransactionViewModelFactory(
    private val dataSource: TransactionDatabaseDao
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

