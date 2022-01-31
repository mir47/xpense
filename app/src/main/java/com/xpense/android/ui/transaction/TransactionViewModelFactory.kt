package com.xpense.android.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xpense.android.data.TransactionRepository

/**
 * This is boiler plate code for a ViewModel Factory.
 *
 * Provides the [TransactionRepository] to the ViewModel.
 */
@Suppress("UNCHECKED_CAST")
class TransactionViewModelFactory(
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

