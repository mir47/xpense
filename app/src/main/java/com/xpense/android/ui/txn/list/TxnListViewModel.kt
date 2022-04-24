package com.xpense.android.ui.txn.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.xpense.android.data.Result.Error
import com.xpense.android.data.Result.Success
import com.xpense.android.data.TransactionRepository
import kotlinx.coroutines.launch

class TxnListViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val transactions = transactionRepository.observeTransactions()

    /**
     * Variable that tells the Fragment to navigate to a specific
     * [com.xpense.android.ui.txn.add_edit.TxnAddEditFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToCreateTransaction = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately navigate to [com.xpense.android.ui.txn.add_edit.TxnAddEditFragment]
     * and call [doneNavigating]
     */
    val navigateToCreateTransaction: LiveData<Boolean>
        get() = _navigateToCreateTransaction

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    val error: LiveData<Boolean> = transactions.map { it is Error }
    val empty: LiveData<Boolean> = transactions.map { (it as? Success)?.data.isNullOrEmpty() }

    /**
     * Call this immediately after navigating to [com.xpense.android.ui.txn.add_edit.TxnAddEditFragment]
     *
     * It will clear the navigation request, so if the device is rotated it won't navigate twice.
     */
    fun doneNavigating() {
        _navigateToCreateTransaction.value = false
    }

    fun navigateToCreateTransaction() {
        _navigateToCreateTransaction.value = true
    }

    fun refresh() {
        _dataLoading.value = true
        viewModelScope.launch {
            transactionRepository.refreshTransactions()
            _dataLoading.value = false
        }
    }

    @Suppress("UNCHECKED_CAST")
    class TxnListViewModelFactory (
        private val transactionRepository: TransactionRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            TxnListViewModel(transactionRepository) as T
    }
}
