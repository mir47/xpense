package com.xpense.android

import android.app.Application
import com.xpense.android.di.AppContainer
import com.xpense.android.di.AppContainerImpl
import com.xpense.android.di.ServiceLocator
import com.xpense.android.domain.repository.TxnRepository
import timber.log.Timber

class XpenseApplication : Application() {

    val txnRepository: TxnRepository
        get() = ServiceLocator.provideTransactionRepository(this)

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        container = AppContainerImpl(this)
    }
}
