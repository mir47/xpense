package com.xpense.android.data.source.remote.model

import java.util.Date

data class TxnDto(
    val id: Long,
    var amount: Double,
    var description: String,
    val createdTimestamp: Date? = null
)
