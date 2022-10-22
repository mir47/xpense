package com.xpense.android.ui.txn.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var uiState by mutableStateOf<UiState>(UiState.Default)
        private set

    var items by mutableStateOf(listOf("Travel", "Food", "Rent"))
        private set

    val amount = mutableStateOf("")
    val description = mutableStateOf("")

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
            uiState = UiState.Done
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
