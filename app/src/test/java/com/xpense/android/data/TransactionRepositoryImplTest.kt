package com.xpense.android.data

import com.xpense.android.BaseTest
import com.xpense.android.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import com.xpense.android.data.Result.Success

@ExperimentalCoroutinesApi
class TransactionRepositoryImplTest: BaseTest() {
    private val transaction1 = Transaction(transactionId = 1)
    private val transaction2 = Transaction(transactionId = 2)
    private val transaction3 = Transaction(transactionId = 3)
    private val localTransactions = listOf(transaction1, transaction2).sortedBy { it.transactionId }
    private val remoteTransactions = listOf(transaction3).sortedBy { it.transactionId }
    private val newTransaction = listOf(transaction3).sortedBy { it.transactionId }

    private lateinit var fakeLocalDataSource: FakeTransactionDataSource
    private lateinit var fakeRemoteDataSource: FakeTransactionDataSource

    // Class under test
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun createRepository() {
        fakeLocalDataSource = FakeTransactionDataSource(localTransactions.toMutableList())
        fakeRemoteDataSource = FakeTransactionDataSource(remoteTransactions.toMutableList())

        // Get reference to the class under test
        repository = TransactionRepositoryImpl(
            fakeLocalDataSource, fakeRemoteDataSource, Dispatchers.Main
        )
    }

    @Test
    fun getTransaction_requestsTransactionFromLocalDataSource() = coroutineTest {
        // When transaction is requested from repository
        val transaction = repository.getTransaction(1)

        // Then transaction is returned
        assertThat((transaction as Success).data, IsEqual(transaction1))
    }

    @Test
    fun getTransactions_requestsAllTransactionsFromLocalDataSource() = coroutineTest {
        // When transactions are requested from repository
        val transactions = repository.getTransactions()

        // Then transactions are loaded from local data source
        assertThat((transactions as Success).data, IsEqual(localTransactions))
    }

    @Test
    fun observeTransactions_requestsAllTransactionsFromLocalDataSource() = coroutineTest {
        // When transactions are observed from repository
        val transactions = repository.observeTransactions().getOrAwaitValue()

        // Then transactions are loaded from local data source
        assertThat((transactions as Success).data, IsEqual(localTransactions))
    }

    @Test
    fun insertTransaction_insertsTransactionInLocalDataSource() = coroutineTest {
        // When transaction is inserted into repository
        repository.saveTransaction(transaction3)

        // Then transaction is added to all transactions
        val transactions = repository.getTransactions()
        assertThat((transactions as Success).data.size, IsEqual(3))
        assertThat(transactions.data[0], IsEqual(transaction1))
        assertThat(transactions.data[1], IsEqual(transaction2))
        assertThat(transactions.data[2], IsEqual(transaction3))
    }

    @Test
    fun updateTransaction_updatesTransactionInLocalDataSource() = coroutineTest {
        // Given transaction with id that exists in data source
        val updatedTransaction1 = Transaction(transactionId = 1, description = "updated")

        // When transaction is updated in repository
        repository.updateTransaction(updatedTransaction1)

        // Then transaction is updated in all transactions
        val transactions = repository.getTransactions()
        assertThat((transactions as Success).data.size, IsEqual(2))
        assertThat(transactions.data[0], IsEqual(transaction2))
        assertThat(transactions.data[1].transactionId, IsEqual(1))
        assertThat(transactions.data[1].description, IsEqual(updatedTransaction1.description))
    }
}
