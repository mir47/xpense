package com.xpense.android.presentation.xperiments.compose.txn_list

import com.xpense.android.domain.model.Txn

data class TxnListState(
    val isLoading: Boolean = false,
    val txns: List<Txn> = emptyList(),
    val error: String = ""
)
