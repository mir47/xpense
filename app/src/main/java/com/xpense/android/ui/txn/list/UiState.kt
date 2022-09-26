package com.xpense.android.ui.txn.list

import com.xpense.android.domain.model.Txn

sealed class UiState {
    object Loading : UiState()
    data class Error(val message: String) : UiState()
    data class Success(val txnsData: List<Txn>) : UiState()
}
