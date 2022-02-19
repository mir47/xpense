package com.xpense.android

import android.app.Application
import com.xpense.android.data.AndroidTransactionRepositoryFake
import com.xpense.android.data.TransactionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class KoinTestApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val fakeRepo: TransactionRepository = AndroidTransactionRepositoryFake()
        val mockModule = module {
            single(override = true) { fakeRepo }
        }

        startKoin {
            androidLogger()
            androidContext(this@KoinTestApp)
            modules(mockModule)
        }
    }

    internal fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}
