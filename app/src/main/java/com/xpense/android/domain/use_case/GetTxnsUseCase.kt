package com.xpense.android.domain.use_case

import com.xpense.android.common.Resource
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.TxnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetTxnsUseCase @Inject constructor(
    private val txnRepo: TxnRepository
) {
    operator fun invoke(): Flow<Resource<List<Txn>>> = flow {
        try {
            emit(Resource.Loading())
            val txns = txnRepo.getTransactions()
            emit(Resource.Success(txns))
        } catch (e: IOException) {
            emit(Resource.Error("error"))
        }
    }
}
