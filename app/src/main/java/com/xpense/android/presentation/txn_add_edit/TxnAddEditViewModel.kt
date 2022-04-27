package com.xpense.android.presentation.txn_add_edit

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xpense.android.data.Result
import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.domain.repository.TxnRepository
import kotlinx.coroutines.launch
import java.util.Date

class TxnAddEditViewModel(
    private val transactionId: Long,
    private val transactionSms: String?,
    private val txnRepository: TxnRepository
) : ViewModel() {

    val amountField = ObservableField<String>()
    val descriptionField = ObservableField<String>()

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
                val txn = txnRepository.getTransaction(transactionId)
                if (txn is Result.Success) {
                    amountField.set(txn.data.amount.toString())
                    descriptionField.set(txn.data.description)
                }
            }
        } else if (transactionSms?.isNotBlank() == true) {
            descriptionField.set(transactionSms)
        }
    }

    fun submit() {
        val description = descriptionField.get().orEmpty()
        val amount = amountField.get()?.toDoubleOrNull() ?: 0.0
        viewModelScope.launch {
            if (transactionId != 0L) {
                val txn = txnRepository.getTransaction(transactionId)
                if (txn is Result.Success) {
                    txnRepository.updateTransaction(
                        txn.data.apply {
                            txn.data.amount = amount
                            txn.data.description = description
                        }
                    )
                }
            } else {
                txnRepository.saveTransaction(
                    TxnEntity(
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
    class TxnAddEditViewModelFactory (
        private val transactionId: Long,
        private val transactionSms: String?,
        private val txnRepository: TxnRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            TxnAddEditViewModel(transactionId, transactionSms, txnRepository) as T
    }
}
