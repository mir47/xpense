package com.xpense.android.di

import android.content.Context
import com.xpense.android.domain.repository.TxnRepository
import com.xpense.android.domain.use_case.get_txns.GetTxnsUseCase

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val txnRepository: TxnRepository
    val getTxnsUseCase: GetTxnsUseCase
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val txnRepository: TxnRepository by lazy {
        ServiceLocator.provideTransactionRepository(applicationContext)
    }

    override val getTxnsUseCase: GetTxnsUseCase
        get() = GetTxnsUseCase(txnRepository)
}
