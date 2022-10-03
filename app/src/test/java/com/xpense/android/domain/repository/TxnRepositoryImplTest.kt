package com.xpense.android.domain.repository

import com.xpense.android.BaseTest
import com.xpense.android.data.source.FakeTxnDataSource
import com.xpense.android.data.Result.Success
import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.model.toTxn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test
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
    private lateinit var repositoryImpl: TxnRepositoryImpl

    @Before
    fun createRepository() {
        fakeLocalDataSource = FakeTxnDataSource(localTransactions.toMutableList())
        fakeRemoteDataSource = FakeTxnDataSource(remoteTransactions.toMutableList())

        // Get reference to the class under test
        repositoryImpl = TxnRepositoryImpl(
            fakeLocalDataSource, fakeRemoteDataSource, Dispatchers.Main
        )
    }

    @Test
    fun getTransaction_requestsTransactionFromLocalDataSource() = runTest {
        // When transaction is requested from repository
        val transaction = repositoryImpl.getTransactionResultById(1)

        // Then transaction is returned
        assertIs<Success<Txn>>(transaction)
        assertEquals(txn1.toTxn(), transaction.data)
    }

    @Test
    fun getTransactions_requestsAllTransactionsFromLocalDataSource() = runTest {
        // When transactions are requested from repository
        val transactions = repositoryImpl.getTransactionsResult()

        // Then transactions are loaded from local data source
        assertIs<Success<List<Txn>>>(transactions)
        assertEquals(localTransactions.map { it.toTxn() }, transactions.data)
    }

    @Test
    fun observeTransactionsResult_requestsAllTransactionsFromLocalDataSource() = runTest {
        // When transactions are observed from repository
        val transactions = repositoryImpl.observeTransactionsResult().first()

        // Then transactions are loaded from local data source
        assertIs<Success<List<Txn>>>(transactions)
        assertEquals(localTransactions.map { it.toTxn() }, transactions.data)
    }

    @Test
    fun insertTransaction_insertsTransactionInLocalDataSource() = runTest {
        // When transaction is inserted into repository
        repositoryImpl.saveTransaction(txn3.toTxn())

        // Then transaction is added to all transactions
        val transactions = repositoryImpl.getTransactionsResult()
        assertIs<Success<List<Txn>>>(transactions)
        assertEquals(3, transactions.data.size)
        assertEquals(txn1.toTxn(), transactions.data[0])
        assertEquals(txn2.toTxn(), transactions.data[1])
        assertEquals(txn3.toTxn(), transactions.data[2])
    }

    @Test
    fun updateTransaction_updatesTransactionInLocalDataSource() = runTest {
        // Given transaction with id that exists in data source
        val updatedTxn1 = Txn(id = 1, description = "updated")

        // When transaction is updated in repository
        repositoryImpl.updateTransaction(updatedTxn1)

        // Then transaction is updated in all transactions
        val transactions = repositoryImpl.getTransactionsResult()
        assertIs<Success<List<Txn>>>(transactions)
        assertEquals(2, transactions.data.size)
        assertEquals(txn2.toTxn(), transactions.data[0])
        assertEquals(1, transactions.data[1].id)
        assertEquals(updatedTxn1.description, transactions.data[1].description)
    }
}
