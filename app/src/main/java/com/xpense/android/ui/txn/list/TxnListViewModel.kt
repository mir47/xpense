package com.xpense.android.ui.txn.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xpense.android.data.Result.Success
import com.xpense.android.domain.use_case.GetTxnsUseCase
import com.xpense.android.domain.use_case.ObserveTxnsResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TxnListViewModel @Inject constructor(
    private val getTxnsUseCase: GetTxnsUseCase,
    private val observeTxnsResultUseCase: ObserveTxnsResultUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<UiState>(UiState.Loading)
    val state: State<UiState> = _state

    init {
        observeTxns()
    }

//    fun getTxns() {
//        getTxnsUseCase().onEach { result ->
//            when (result) {
//                is Resource.Success -> _state.value =
//                    UiState(txns = result.data ?: emptyList())
//                is Resource.Error -> _state.value =
//                    UiState(error = result.message ?: "An unexpected error occurred")
//                is Resource.Loading -> _state.value =
//                    UiState(isLoading = true)
//            }
//        }.launchIn(viewModelScope)
//    }

    private fun observeTxns() {
        observeTxnsResultUseCase().onEach { result ->
            if (result is Success) {
                _state.value = UiState.Success(txnsData = result.data)
            }
        }.launchIn(viewModelScope)
    }
}
