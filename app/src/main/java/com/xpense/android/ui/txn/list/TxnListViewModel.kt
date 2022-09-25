package com.xpense.android.ui.txn.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xpense.android.common.Resource
import com.xpense.android.domain.use_case.get_txns.GetTxnsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TxnListViewModel(
    private val getTxnsUseCase: GetTxnsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(TxnListState())
    val state: State<TxnListState> = _state

    init {
        getTxns()
    }

    fun getTxns() {
        getTxnsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> _state.value =
                    TxnListState(txns = result.data ?: emptyList())
                is Resource.Error -> _state.value =
                    TxnListState(error = result.message ?: "An unexpected error occurred")
                is Resource.Loading -> _state.value =
                    TxnListState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Factory for TxnListViewModel that takes GetTxnsUseCase as a dependency
     */
    companion object {
        fun provideFactory(
            getTxnsUseCase: GetTxnsUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TxnListViewModel(getTxnsUseCase) as T
            }
        }
    }
}
