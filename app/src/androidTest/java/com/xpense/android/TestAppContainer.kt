package com.xpense.android

import android.content.Context
import com.xpense.android.di.AppContainer
import com.xpense.android.domain.repository.FakeAndroidTxnRepository
import com.xpense.android.domain.use_case.get_txns.GetTxnsUseCase

class TestAppContainer(private val context: Context) : AppContainer {

    override val txnRepository = FakeAndroidTxnRepository()

    override val getTxnsUseCase = GetTxnsUseCase(txnRepository)
}
