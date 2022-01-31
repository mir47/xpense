package com.xpense.android

import android.app.Application
import com.xpense.android.data.TransactionRepository

class XpenseApplication : Application() {

    val transactionRepository: TransactionRepository
        get() = ServiceLocator.provideTransactionRepository(this)

}