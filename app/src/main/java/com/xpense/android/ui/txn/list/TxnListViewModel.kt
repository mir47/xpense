package com.xpense.android.ui.txn.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xpense.android.data.Result
import com.xpense.android.domain.use_case.ObserveTxnsResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TxnListViewModel @Inject constructor(
    private val observeTxnsResultUseCase: ObserveTxnsResultUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<UiState>(UiState.Loading)
    val state: State<UiState> = _state

    init {
        observeTxns()
    }

    private fun observeTxns() {
        observeTxnsResultUseCase().onEach { result ->
            _state.value = when (result) {
                is Result.Success -> UiState.Success(txnsData = result.data)
                is Result.Error -> UiState.Error(message = result.exception.message ?: "An unexpected error occurred")
                is Result.Loading -> UiState.Loading
            }
        }.launchIn(viewModelScope)
    }
}
