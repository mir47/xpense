package com.xpense.android

import android.app.Application
import com.xpense.android.data.TransactionDataSource
import com.xpense.android.data.TransactionRepository
import com.xpense.android.data.TransactionRepositoryImpl
import com.xpense.android.data.source.local.TransactionDatabase
import com.xpense.android.data.source.local.TransactionDataSourceLocal
import com.xpense.android.ui.transactions.TransactionsViewModel
import com.xpense.android.ui.addedittransaction.AddEditTransactionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class XpenseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("test timber debug log")
        }

        /**
         * use Koin Library as a service locator
         */
        val appModule = module {
            // Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel { TransactionsViewModel(get()) }
            viewModel { (transactionId: Long) -> AddEditTransactionViewModel(transactionId, get()) }
            // Declare singleton definitions to be later injected using by inject()
            // This view model is declared singleton to be used across multiple fragments
            single<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }
            single<TransactionDataSource> { TransactionDataSourceLocal(get()) }
            single { TransactionDatabase.createTransactionDao(this@XpenseApplication) }
        }

        startKoin {
            androidContext(this@XpenseApplication)
            modules(appModule)
        }
    }
}
