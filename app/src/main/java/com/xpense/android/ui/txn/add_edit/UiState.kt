package com.xpense.android.ui.txn.add_edit

sealed class UiState {
    object Default : UiState()
    object Loading : UiState()
    object Error : UiState()
    object Done : UiState()
}
