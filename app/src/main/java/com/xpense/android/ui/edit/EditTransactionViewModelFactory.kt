package com.xpense.android.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xpense.android.db.TransactionDatabaseDao

/**
 * This is boiler plate code for a ViewModel Factory.
 *
 * Provides the TransactionDatabaseDao to the ViewModel.
 */
class EditTransactionViewModelFactory(
    private val dataSource: TransactionDatabaseDao
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTransactionViewModel::class.java)) {
            return EditTransactionViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

