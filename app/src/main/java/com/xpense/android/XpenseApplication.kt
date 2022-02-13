package com.xpense.android

import android.app.Application
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.TransactionRepository
import com.xpense.android.data.TransactionRepositoryImpl
import com.xpense.android.data.local.TransactionDatabase
import com.xpense.android.data.local.TransactionDataSourceLocal
import com.xpense.android.ui.main.MainViewModel
import com.xpense.android.ui.transaction.TransactionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class XpenseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.i("test log")

        /**
         * use Koin Library as a service locator
         */
        val appModule = module {
            // Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel { MainViewModel(get()) }
            viewModel { (transactionId: Long) -> TransactionViewModel(transactionId, get()) }
            // Declare singleton definitions to be later injected using by inject()
            // This view model is declared singleton to be used across multiple fragments
            single { TransactionRepositoryImpl(get(), get()) as TransactionRepository }
            single { TransactionDataSourceLocal(get()) as TransactionDataSource }
            single { TransactionDatabase.createTransactionDao(this@XpenseApplication) }
        }

        startKoin {
            androidContext(this@XpenseApplication)
            modules(appModule)
        }
    }
}
