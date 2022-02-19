package com.xpense.android.ui.transactions

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xpense.android.R
import com.xpense.android.data.AndroidTransactionRepositoryFake
import com.xpense.android.data.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
//@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
@ExperimentalCoroutinesApi
class TransactionsFragmentTest : KoinTest {

    private lateinit var repository: TransactionRepository

    @Before
    fun setup() {
        repository = AndroidTransactionRepositoryFake()
//        startKoin()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() {
        // WHEN - Details fragment launched to display task
        launchFragmentInContainer<TransactionsFragment>(Bundle(), R.style.Theme_Xpense)

        Thread.sleep(2000)
    }
}
