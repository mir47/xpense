package com.xpense.android.domain.use_case

import com.xpense.android.domain.repository.TxnRepository
import javax.inject.Inject

interface GetTxnUseCase {
    operator fun invoke()
}

class GetTxnUseCaseImpl @Inject constructor(
    private val txnRepo: TxnRepository
): GetTxnUseCase {
    override operator fun invoke() = Unit
}
