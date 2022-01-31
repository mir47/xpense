package com.xpense.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xpense.android.data.local.TransactionDao

/**
 * This is boiler plate code for a ViewModel Factory.
 *
 * Provides the TransactionDatabaseDao to the ViewModel.
 */
class MainViewModelFactory(
    private val dataSource: TransactionDao
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

