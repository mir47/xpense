package com.xpense.android.domain.use_case.get_txns

import com.xpense.android.common.Resource
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.TxnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetTxnsUseCase(
    private val txnRepo: TxnRepository
) {
    operator fun invoke(): Flow<Resource<List<Txn>>> = flow {
        try {
            emit(Resource.Loading())
            val txns = txnRepo.getTxns()
            emit(Resource.Success(txns))
        } catch (e: IOException) {
            emit(Resource.Error("error"))
        }
    }
}
