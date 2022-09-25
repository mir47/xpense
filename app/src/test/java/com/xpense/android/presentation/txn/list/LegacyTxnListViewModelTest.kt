package com.xpense.android.presentation.txn.list

import com.xpense.android.BaseTest
import com.xpense.android.data.Result.Success
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.FakeTxnRepository
import com.xpense.android.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class TxnListViewModelTest : BaseTest() {

    // Use a fake repository to be injected into the ViewModel
    private lateinit var fakeRepository: FakeTxnRepository

    // Subject under test
    private lateinit var viewModel: TxnListViewModel

    @Before
    fun setupViewModel() {
        // We initialise the transactions to 3
        fakeRepository = FakeTxnRepository()
        val txn1 = Txn(id = 1)
        val txn2 = Txn(id = 2)
        val txn3 = Txn(id = 3)
        fakeRepository.addTransactions(txn1, txn2, txn3)

//        viewModel = TxnListViewModel(fakeRepository)
    }

//    @Test
//    fun transactions_returnsAllTransactions() {
//        // When
//        val transactions = viewModel.transactions.getOrAwaitValue()
//
//        // Then
//        assertIs<Success<List<Txn>>>(transactions)
//        assertEquals(3, transactions.data.size)
//    }
//
//    @Test
//    fun clickFab_navigateToCreateTransaction() {
//        // When FAB clicked
//        viewModel.onFabClick()
//
//        // Then nav live data is set
//        val navOn = viewModel.navigateToTxnAddEdit.getOrAwaitValue()
//        assertTrue(navOn)
//
//        // When done navigating
//        viewModel.doneNavigating()
//
//        // Then nav live data is cleared
//        val navOff = viewModel.navigateToTxnAddEdit.getOrAwaitValue()
//        assertFalse(navOff)
//    }
//
//    @Test
//    fun refresh_loading() {
//        // Pause dispatcher to verify initial values
//        pauseCoroutine()
//
//        // When view model is refreshed
//        viewModel.refresh()
//
//        // Then assert that the progress indicator is shown
//        assertTrue(viewModel.dataLoading.getOrAwaitValue())
//
//        // Execute pending coroutines actions
//        resumeCoroutine()
//
//        // Then assert that the progress indicator is hidden
//        assertFalse(viewModel.dataLoading.getOrAwaitValue())
//    }
//
//    @Test
//    fun loadStatisticsWhenTasksAreUnavailable_callErrorToDisplay() {
//        // Make the repository return errors
//        fakeRepository.shouldReturnError = true
//
//        viewModel.refresh()
//
//        // Then empty and error are true (which triggers an error message to be shown).
//        assertTrue(viewModel.empty.getOrAwaitValue())
//        assertTrue(viewModel.error.getOrAwaitValue())
//    }
}
