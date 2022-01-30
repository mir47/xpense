package com.xpense.android.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xpense.android.db.TransactionDatabaseDao

class MainViewModel(database: TransactionDatabaseDao) : ViewModel() {
    val transactions = database.getAllTransactions()

    /**
     * Variable that tells the Fragment to navigate to a specific [com.xpense.android.ui.transaction.TransactionFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToTransaction = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately navigate to [com.xpense.android.ui.transaction.TransactionFragment] and
     * call [doneNavigating]
     */
    val navigateToTransaction: LiveData<Boolean>
        get() = _navigateToTransaction

    /**
     * Call this immediately after navigating to [com.xpense.android.ui.transaction.TransactionFragment]
     *
     * It will clear the navigation request, so if the device is rotated it won't navigate twice.
     */
    fun doneNavigating() {
        _navigateToTransaction.value = false
    }

    fun navigateToTransaction() {
        _navigateToTransaction.value = true
    }
}