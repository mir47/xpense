package com.xpense.android.ui.transactions

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xpense.android.R
import com.xpense.android.ServiceLocator
import com.xpense.android.data.FakeAndroidTransactionRepository
import com.xpense.android.data.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TransactionsFragmentTest {

    private lateinit var repository: TransactionRepository

    @Before
    fun setup() {
        repository = FakeAndroidTransactionRepository()
        ServiceLocator.repository = repository
    }

    @After
    fun cleanUp() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() = runBlockingTest {
        // WHEN - Details fragment launched to display task
        launchFragmentInContainer<TransactionsFragment>(Bundle(), R.style.Theme_Xpense)

        Thread.sleep(2000)
    }
}
