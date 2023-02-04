package com.xpense.android.di

import com.xpense.android.domain.use_case.DeleteTxnsUseCase
import com.xpense.android.domain.use_case.DeleteTxnsUseCaseImpl
import com.xpense.android.domain.use_case.GetTxnUseCase
import com.xpense.android.domain.use_case.GetTxnUseCaseImpl
import com.xpense.android.domain.use_case.GetTxnsUseCase
import com.xpense.android.domain.use_case.GetTxnsUseCaseImpl
import com.xpense.android.domain.use_case.ObserveTxnsResultUseCase
import com.xpense.android.domain.use_case.ObserveTxnsResultUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {

    @Binds
    abstract fun bindObserveTxnsResultUseCase(
        observeTxnsResultUseCaseImpl: ObserveTxnsResultUseCaseImpl
    ): ObserveTxnsResultUseCase

    @Binds
    abstract fun bindGetTxnUseCase(
        getTxnUseCaseImpl: GetTxnUseCaseImpl
    ): GetTxnUseCase

    @Binds
    abstract fun bindGetTxnsUseCase(
        getTxnsUseCaseImpl: GetTxnsUseCaseImpl
    ): GetTxnsUseCase

    @Binds
    abstract fun bindDeleteTxnsUseCase(
        deleteTxnsUseCaseImpl: DeleteTxnsUseCaseImpl
    ): DeleteTxnsUseCase

}
