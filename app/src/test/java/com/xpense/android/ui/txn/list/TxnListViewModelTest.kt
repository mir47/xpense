package com.xpense.android.ui.txn.list

import com.xpense.android.BaseTest
import com.xpense.android.data.Transaction
import com.xpense.android.data.FakeTransactionRepository
import com.xpense.android.data.Result.Success
import com.xpense.android.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TxnListViewModelTest : BaseTest() {

    // Use a fake repository to be injected into the ViewModel
    private lateinit var fakeRepository: FakeTransactionRepository

    // Subject under test
    private lateinit var viewModel: TxnListViewModel

    @Before
    fun setupViewModel() {
        // We initialise the transactions to 3
        fakeRepository = FakeTransactionRepository()
        val task1 = Transaction(transactionId = 1)
        val task2 = Transaction(transactionId = 2)
        val task3 = Transaction(transactionId = 3)
        fakeRepository.addTasks(task1, task2, task3)

        viewModel = TxnListViewModel(fakeRepository)
    }

    @Test
    fun transactions_returnsAllTransactions() {
        // When
        val transactions = viewModel.transactions.getOrAwaitValue()

        // Then
        assertThat((transactions as Success).data.size, IsEqual(3))
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

    @Test
    fun refresh_loading() {
        // Pause dispatcher to verify initial values
        pauseCoroutine()

        // When view model is refreshed
        viewModel.refresh()

        // Then assert that the progress indicator is shown
        assertThat(viewModel.dataLoading.getOrAwaitValue(), `is`(true))

        // Execute pending coroutines actions
        resumeCoroutine()

        // Then assert that the progress indicator is hidden
        assertThat(viewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun loadStatisticsWhenTasksAreUnavailable_callErrorToDisplay() {
        // Make the repository return errors
        fakeRepository.setReturnError(true)

        viewModel.refresh()

        // Then empty and error are true (which triggers an error message to be shown).
        assertThat(viewModel.empty.getOrAwaitValue(), `is`(true))
        assertThat(viewModel.error.getOrAwaitValue(), `is`(true))
    }
}
