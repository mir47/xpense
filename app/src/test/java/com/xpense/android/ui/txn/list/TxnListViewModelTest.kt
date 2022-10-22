package com.xpense.android.ui.txn.list

import com.xpense.android.BaseTest
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.FakeTxnRepository
import com.xpense.android.domain.use_case.DeleteTxnsUseCase
import com.xpense.android.domain.use_case.ObserveTxnsResultUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class TxnListViewModelTest : BaseTest() {

    // Use a fake repository to be injected into the use cases
    private lateinit var fakeRepository: FakeTxnRepository

    // use cases to be injected into the ViewModel
    // TODO: possibility of creating fake use cases?
    private lateinit var observeTxnsResultUseCase: ObserveTxnsResultUseCase
    private lateinit var deleteTxnsUseCase: DeleteTxnsUseCase

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

        observeTxnsResultUseCase = ObserveTxnsResultUseCase(fakeRepository)
        deleteTxnsUseCase = DeleteTxnsUseCase(fakeRepository)

        viewModel = TxnListViewModel(observeTxnsResultUseCase, deleteTxnsUseCase)
    }

    @Test
    fun state_returnsAllTransactions() = testCoroutineRule.runTest {
        // When
        var state = viewModel.state

        // Then
        assertIs<UiState.Loading>(state)

        advanceUntilIdle()
        state = viewModel.state
        assertIs<UiState.Success>(state)
        assertEquals(3, state.txnsData.size)
    }
}
