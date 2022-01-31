package com.xpense.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xpense.android.data.TransactionRepository

/**
 * This is boiler plate code for a ViewModel Factory.
 *
 * Provides the [TransactionRepository] to the ViewModel.
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

