package com.xpense.android.domain.use_case

import com.xpense.android.domain.repository.TxnRepository
import javax.inject.Inject

class DeleteTxnsUseCase @Inject constructor(
    private val txnRepo: TxnRepository
) {
    suspend operator fun invoke() = txnRepo.deleteAllTransactions()
}
