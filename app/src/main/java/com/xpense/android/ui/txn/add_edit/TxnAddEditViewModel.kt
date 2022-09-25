package com.xpense.android.ui.txn.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xpense.android.data.Result
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.TxnRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.Date

class TxnAddEditViewModel @AssistedInject constructor(
    private val txnRepo: TxnRepository,
    @Assisted private val txnId: Long,
) : ViewModel() {

    val amount = mutableStateOf("")
    val description = mutableStateOf("")

    private val _items = mutableStateOf(listOf("Travel", "Food", "Rent"))
    val items: State<List<String>> =_items

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
        if (txnId != 0L) {
            viewModelScope.launch {
                val txn = txnRepo.getTransactionResultById(txnId)
                if (txn is Result.Success) {
                    amount.value = txn.data.amount.toString()
                    description.value = txn.data.description
                }
            }
        }
    }

    fun submit() {
        val description = description
        val amount = amount.value.toDoubleOrNull() ?: 0.0
        viewModelScope.launch {
            if (txnId != 0L) {
                val txn = txnRepo.getTransactionResultById(txnId)
                if (txn is Result.Success) {
                    txnRepo.updateTransaction(
                        txn.data.apply {
                            txn.data.amount = amount
                            txn.data.description = description.value
                        }
                    )
                }
            } else {
                txnRepo.saveTransaction(
                    Txn(
                        id = 0,
                        createdTimestamp = Date(System.currentTimeMillis()),
                        amount = amount,
                        description = description.value
                    )
                )
            }
            navigateExit()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(txnId: Long): TxnAddEditViewModel
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            txnId: Long,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(txnId) as T
            }
        }
    }
}
