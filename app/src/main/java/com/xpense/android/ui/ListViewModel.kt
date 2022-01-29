package com.xpense.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    /**
     * Variable that tells the Fragment to navigate to a specific [DetailFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToDetail = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately navigate to [DetailFragment] and call [doneNavigating]
     */
    val navigateToDetail: LiveData<Boolean>
        get() = _navigateToDetail

    /**
     * Call this immediately after navigating to [DetailFragment]
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