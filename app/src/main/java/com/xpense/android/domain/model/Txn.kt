package com.xpense.android.domain.model

import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.data.source.remote.model.TxnDto
import java.util.Date

data class Txn(
    val id: Long,
    var amount: Double = 0.0,
    var description: String = "",
    val createdTimestamp: Date? = null
)

fun Txn.toTxnEntity(): TxnEntity {
    return TxnEntity(
        transactionId = id,
        amount = amount,
        description = description,
        createdTimestamp = createdTimestamp
    )
}

fun TxnEntity.toTxn(): Txn {
    return Txn(
        id = transactionId,
        amount = amount,
        description = description,
        createdTimestamp = createdTimestamp
    )
}

fun Txn.toTxnDto(): TxnDto {
    return TxnDto(
        id = id,
        amount = amount,
        description = description,
        createdTimestamp = createdTimestamp
    )
}

fun TxnDto.toTxn(): Txn {
    return Txn(
        id = id,
        amount = amount,
        description = description,
        createdTimestamp = createdTimestamp
    )
}
