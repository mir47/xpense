package com.xpense.android.domain.use_case

import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.TxnRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTxnsUseCase @Inject constructor(
    private val txnRepo: TxnRepository
) {
    operator fun invoke(): Flow<List<Txn>> =
        txnRepo.observeTransactionsFlow()
}
