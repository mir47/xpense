package com.xpense.android.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xpense.android.db.TransactionDatabaseDao

class ListViewModel(database: TransactionDatabaseDao) : ViewModel() {
    val transactions = database.getAllTransactions()

    /**
     * Variable that tells the Fragment to navigate to a specific [com.xpense.android.ui.DetailFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToDetail = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately navigate to [com.xpense.android.ui.DetailFragment] and
     * call [doneNavigating]
     */
    val navigateToDetail: LiveData<Boolean>
        get() = _navigateToDetail

    /**
     * Call this immediately after navigating to [com.xpense.android.ui.DetailFragment]
     *
     * It will clear the navigation request, so if the device is rotated it won't navigate twice.
     */
    fun doneNavigating() {
        _navigateToDetail.value = false
    }

    fun navigateToDetail() {
        _navigateToDetail.value = true
    }
}