package com.xpense.android.ui.transactions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.xpense.android.data.Transaction
import com.xpense.android.data.FakeTransactionRepository
import com.xpense.android.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TransactionsViewModelTest {

    // Use a fake repository to be injected into the ViewModel
    private lateinit var fakeRepository: FakeTransactionRepository

    // Required for LiveData testing
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var viewModel: TransactionsViewModel

    @Before
    fun setupViewModel() {
        // We initialise the transactions to 3
        fakeRepository = FakeTransactionRepository()
        val task1 = Transaction(transactionId = 1)
        val task2 = Transaction(transactionId = 2)
        val task3 = Transaction(transactionId = 3)
        fakeRepository.addTasks(task1, task2, task3)

        viewModel = TransactionsViewModel(fakeRepository)
    }

    @Test
    fun transactions_returnsAllTransactions() {
        // When
        val transactions = viewModel.transactions.getOrAwaitValue()

        // Then
        assertThat(transactions.size, IsEqual(3))
    }

    @Test
    fun navigateToCreateTransaction_() {
        // When navigate called
        viewModel.navigateToCreateTransaction()

        // Then nav live data is set
        val navOn = viewModel.navigateToCreateTransaction.getOrAwaitValue()
        assertThat(navOn, IsEqual(true))

        // When done navigating
        viewModel.doneNavigating()

        // Then nav live data is cleared
        val navOff = viewModel.navigateToCreateTransaction.getOrAwaitValue()
        assertThat(navOff, IsEqual(false))
    }
}
