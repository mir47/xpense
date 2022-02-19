package com.xpense.android.ui.transactions

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.xpense.android.R
import com.xpense.android.data.AndroidTransactionRepositoryFake
import com.xpense.android.data.Transaction
import com.xpense.android.data.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
@ExperimentalCoroutinesApi
class TransactionsFragmentTest : KoinTest {

    private lateinit var fakeRepository: AndroidTransactionRepositoryFake

    private lateinit var mockModule: Module

    // Required for LiveData testing
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() = runBlockingTest {
        fakeRepository = AndroidTransactionRepositoryFake()
        val task1 = Transaction(transactionId = 1, amount = 1.0, description = "Test 1")
        val task2 = Transaction(transactionId = 2, amount = 2.0, description = "Test 2")
        val task3 = Transaction(transactionId = 3, amount = 3.0, description = "Test 3")
        fakeRepository.addTasks(task1, task2, task3)

        mockModule = module {
            single(override = true) { fakeRepository as TransactionRepository }
        }
        // TODO:
        //  this is unsafe... recommended approach is to add a module of
        //  overrides to modules used by startKoin in Application class:
        //  https://insert-koin.io/docs/reference/koin-android/instrumented-testing/
        loadKoinModules(mockModule)
    }

    @After
    fun tearDown() {
        // TODO:
        //  this is unsafe... recommended approach is to add a module of
        //  overrides to modules used by startKoin in Application class:
        //  https://insert-koin.io/docs/reference/koin-android/instrumented-testing/
        unloadKoinModules(mockModule)

        stopKoin()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() {
        // WHEN - Details fragment launched to display task
        launchFragmentInContainer<TransactionsFragment>(Bundle(), R.style.Theme_Xpense)

        Thread.sleep(2000)
    }
}
