package com.xpense.android.domain.use_case

import com.xpense.android.data.Result
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.TxnRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveTxnsResultUseCase {
    operator fun invoke(): Flow<Result<List<Txn>>>
}

class ObserveTxnsResultUseCaseImpl @Inject constructor(
    private val txnRepo: TxnRepository
) : ObserveTxnsResultUseCase {
    override operator fun invoke(): Flow<Result<List<Txn>>> =
        txnRepo.observeTransactionsResult()
}
