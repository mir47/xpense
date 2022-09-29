package com.xpense.android.domain.repository

import com.xpense.android.BaseTest
import com.xpense.android.data.FakeTxnDataSource
import com.xpense.android.data.Result.Success
import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.model.toTxn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class TxnRepositoryImplTest: BaseTest() {
    private val txn1 = TxnEntity(transactionId = 1)
    private val txn2 = TxnEntity(transactionId = 2)
    private val txn3 = TxnEntity(transactionId = 3)
    private val localTransactions = listOf(txn1, txn2).sortedBy { it.transactionId }
    private val remoteTransactions = listOf(txn3).sortedBy { it.transactionId }
    private val newTransaction = listOf(txn3).sortedBy { it.transactionId }

    private lateinit var fakeLocalDataSource: FakeTxnDataSource
    private lateinit var fakeRemoteDataSource: FakeTxnDataSource

    // Class under test
    private lateinit var repository: TxnRepositoryImpl

    @Before
    fun createRepository() {
        fakeLocalDataSource = FakeTxnDataSource(localTransactions.toMutableList())
        fakeRemoteDataSource = FakeTxnDataSource(remoteTransactions.toMutableList())

        // Get reference to the class under test
        repository = TxnRepositoryImpl(
            fakeLocalDataSource, fakeRemoteDataSource, Dispatchers.Main
        )
    }

    @Test
    fun getTransaction_requestsTransactionFromLocalDataSource() = coroutineTest {
        // When transaction is requested from repository
        val transaction = repository.getTransactionResultById(1)

        // Then transaction is returned
        assertIs<Success<Txn>>(transaction)
        assertEquals(txn1.toTxn(), transaction.data)
    }

    @Test
    fun getTransactions_requestsAllTransactionsFromLocalDataSource() = coroutineTest {
        // When transactions are requested from repository
        val transactions = repository.getTransactionsResult()

        // Then transactions are loaded from local data source
        assertIs<Success<List<Txn>>>(transactions)
        assertEquals(localTransactions.map { it.toTxn() }, transactions.data)
    }

    @Ignore("migrate live data to flow")
    @Test
    fun observeTransactions_requestsAllTransactionsFromLocalDataSource() = coroutineTest {
//        // When transactions are observed from repository
//        val transactions = repository.observeTransactionsResult().getOrAwaitValue()
//
//        // Then transactions are loaded from local data source
//        assertIs<Success<List<Txn>>>(transactions)
//        assertEquals(localTransactions.map { it.toTxn() }, transactions.data)
    }

    @Test
    fun insertTransaction_insertsTransactionInLocalDataSource() = coroutineTest {
        // When transaction is inserted into repository
        repository.saveTransaction(txn3.toTxn())

        // Then transaction is added to all transactions
        val transactions = repository.getTransactionsResult()
        assertIs<Success<List<Txn>>>(transactions)
        assertEquals(3, transactions.data.size)
        assertEquals(txn1.toTxn(), transactions.data[0])
        assertEquals(txn2.toTxn(), transactions.data[1])
        assertEquals(txn3.toTxn(), transactions.data[2])
    }

    @Test
    fun updateTransaction_updatesTransactionInLocalDataSource() = coroutineTest {
        // Given transaction with id that exists in data source
        val updatedTxn1 = Txn(id = 1, description = "updated")

        // When transaction is updated in repository
        repository.updateTransaction(updatedTxn1)

        // Then transaction is updated in all transactions
        val transactions = repository.getTransactionsResult()
        assertIs<Success<List<Txn>>>(transactions)
        assertEquals(2, transactions.data.size)
        assertEquals(txn2.toTxn(), transactions.data[0])
        assertEquals(1, transactions.data[1].id)
        assertEquals(updatedTxn1.description, transactions.data[1].description)
    }
}
