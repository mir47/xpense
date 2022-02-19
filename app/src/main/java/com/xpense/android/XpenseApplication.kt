package com.xpense.android

import android.app.Application
import com.xpense.android.data.TransactionRepository
import timber.log.Timber

class XpenseApplication : Application() {

    val transactionRepository: TransactionRepository
        get() = ServiceLocator.provideTransactionRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
