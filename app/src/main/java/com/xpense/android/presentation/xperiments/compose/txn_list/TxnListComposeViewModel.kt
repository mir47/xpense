package com.xpense.android.presentation.xperiments.compose.txn_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xpense.android.common.Resource
import com.xpense.android.domain.use_case.get_txns.GetTxnsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TxnListComposeViewModel(
    private val getTxnsUseCase: GetTxnsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(TxnListState())
    val state: State<TxnListState> = _state

    init {
        getTxns()
    }

    private fun getTxns() {
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
}
