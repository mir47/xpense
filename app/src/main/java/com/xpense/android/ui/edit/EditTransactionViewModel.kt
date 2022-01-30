package com.xpense.android.ui.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xpense.android.db.Transaction
import com.xpense.android.db.TransactionDatabaseDao
import kotlinx.coroutines.launch
import java.lang.Exception

class EditTransactionViewModel(private val database: TransactionDatabaseDao) : ViewModel() {

    val amount = ObservableField<String>()

    /**
     * Variable that tells the Fragment to close.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateExit = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately pop the fragment off the backstack and call [doneNavigating]
     */
    val navigateExit: LiveData<Boolean>
        get() = _navigateExit

    /**
     * Call this immediately after navigating
     *
     * It will clear the navigation request, so if the device is rotated it won't navigate twice.
     */
    fun doneNavigating() {
        _navigateExit.value = false
    }

    private fun navigateExit() {
        _navigateExit.value = true
    }

    fun submit() {
        try {
            amount.get()?.toDouble()?.let {
                viewModelScope.launch {
                    database.insert(Transaction(amount = it))
                    navigateExit()
                }
            }
        } catch (e: Exception) {
        }
    }
}