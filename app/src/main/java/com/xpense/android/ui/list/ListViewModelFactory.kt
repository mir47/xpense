package com.xpense.android.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xpense.android.db.TransactionDatabaseDao

/**
 * This is boiler plate code for a ViewModel Factory.
 *
 * Provides the TransactionDatabaseDao to the ViewModel.
 */
class ListViewModelFactory(
    private val dataSource: TransactionDatabaseDao
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

