package com.xpense.android.ui.txn.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xpense.android.data.Result
import com.xpense.android.domain.use_case.DeleteTxnsUseCase
import com.xpense.android.domain.use_case.ObserveTxnsResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TxnListViewModel @Inject constructor(
    private val observeTxnsResultUseCase: ObserveTxnsResultUseCase,
    private val deleteTxnsUseCase: DeleteTxnsUseCase,
) : ViewModel() {

    var state by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        observeTxns()
    }

    fun deleteAllTxns() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTxnsUseCase()
        }
    }

    private fun observeTxns() {
        observeTxnsResultUseCase().onEach { result ->
            state = when (result) {
                is Result.Success -> UiState.Success(txnsData = result.data)
                is Result.Error -> UiState.Error(message = result.exception.message ?: "An unexpected error occurred")
                is Result.Loading -> UiState.Loading
            }
        }.launchIn(viewModelScope)
    }
}
