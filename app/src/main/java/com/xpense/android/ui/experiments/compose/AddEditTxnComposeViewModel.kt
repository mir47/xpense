package com.xpense.android.ui.experiments.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xpense.android.data.Result
import com.xpense.android.data.Transaction
import com.xpense.android.data.TransactionRepository
import kotlinx.coroutines.launch
import java.util.Date

class AddEditTxnComposeViewModel(
    private val transactionId: Long,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    // TODO: use state instead of public fields
    var amount: String = ""
    var description: String = ""

//    val amountField = ObservableField<String>()
//    val descriptionField = ObservableField<String>()

    /**
     * Variable that tells the Fragment to close.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    // TODO: maybe use toSingleEvent()
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

    init {
        if (transactionId != 0L) {
            viewModelScope.launch {
                val txn = transactionRepository.getTransaction(transactionId)
                if (txn is Result.Success) {
                    amount = txn.data.amount.toString()
                    description = txn.data.description
                }
            }
        }
    }

    fun submit() {
        val description = description
        val amount = amount.toDoubleOrNull() ?: 0.0
        viewModelScope.launch {
            if (transactionId != 0L) {
                val txn = transactionRepository.getTransaction(transactionId)
                if (txn is Result.Success) {
                    transactionRepository.updateTransaction(
                        txn.data.apply {
                            txn.data.amount = amount
                            txn.data.description = description
                        }
                    )
                }
            } else {
                transactionRepository.saveTransaction(
                    Transaction(
                        createdTimestamp = Date(System.currentTimeMillis()),
                        amount = amount,
                        description = description
                    )
                )
            }
            navigateExit()
        }
    }

    @Suppress("UNCHECKED_CAST")
    class AddEditTxnComposeViewModelFactory (
        private val transactionId: Long,
        private val transactionRepository: TransactionRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            AddEditTxnComposeViewModel(transactionId, transactionRepository) as T
    }
}
