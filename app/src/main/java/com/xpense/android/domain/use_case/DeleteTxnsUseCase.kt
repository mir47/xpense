package com.xpense.android.domain.use_case

import com.xpense.android.domain.repository.TxnRepository
import javax.inject.Inject

interface DeleteTxnsUseCase {
    suspend operator fun invoke()
}

class DeleteTxnsUseCaseImpl @Inject constructor(
    private val txnRepo: TxnRepository
): DeleteTxnsUseCase {
    override suspend operator fun invoke() = txnRepo.deleteAllTransactions()
}
