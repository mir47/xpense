package com.xpense.android.domain.use_case

import com.xpense.android.BaseTest
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.FakeTxnRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetTxnsUseCaseTest: BaseTest() {

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeTxnRepository

    // Subject under test
    private lateinit var useCase: GetTxnsUseCase

    @Before
    fun setupUseCase() {
        // We initialise the transactions to 3
        fakeRepository = FakeTxnRepository()
        val txn1 = Txn(id = 1)
        val txn2 = Txn(id = 2)
        val txn3 = Txn(id = 3)
        fakeRepository.addTransactions(txn1, txn2, txn3)

        useCase = GetTxnsUseCase(fakeRepository)
    }

    @Test
    fun invoke_returnsAllTransactions() {
//        // When
//        val transactions = viewModel.transactions.getOrAwaitValue()
//
//        // Then
//        assertIs<Success<List<Txn>>>(transactions)
//        assertEquals(3, transactions.data.size)
    }
}
