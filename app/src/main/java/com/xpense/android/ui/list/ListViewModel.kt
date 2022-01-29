package com.xpense.android.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xpense.android.db.TransactionDatabaseDao

class ListViewModel(database: TransactionDatabaseDao) : ViewModel() {
    val transactions = database.getAllTransactions()

    /**
     * Variable that tells the Fragment to navigate to a specific [com.xpense.android.ui.edit.EditTransactionFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToEdit = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately navigate to [com.xpense.android.ui.edit.EditTransactionFragment] and
     * call [doneNavigating]
     */
    val navigateToEdit: LiveData<Boolean>
        get() = _navigateToEdit

    /**
     * Call this immediately after navigating to [com.xpense.android.ui.edit.EditTransactionFragment]
     *
     * It will clear the navigation request, so if the device is rotated it won't navigate twice.
     */
    fun doneNavigating() {
        _navigateToEdit.value = false
    }

    fun navigateToEdit() {
        _navigateToEdit.value = true
    }
}