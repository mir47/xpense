package com.xpense.android.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xpense.android.data.Result.Success
import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TxnDataSourceLocalTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: TxnDataSourceLocal

    private lateinit var database: TxnDatabase

    @Before
    fun initDb() {
        // Using an in-memory database for testing, because it doesn't survive killing the process.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TxnDatabase::class.java
        ).allowMainThreadQueries().build()

        localDataSource =
            TxnDataSourceLocal(
                database.txnDao(),
                Dispatchers.Main
            )
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveTransaction_retrievesTransaction() = runBlockingTest {
        // GIVEN - A new transaction saved in the data store.
        val txn = TxnEntity(transactionId = 1, amount = 12.34, description = "description")
        localDataSource.saveTransaction(txn)

        // WHEN - Transaction retrieved by ID.
        val result = localDataSource.getTransactionResultById(txn.transactionId)

        // THEN - Same transaction is returned.
        assertTrue(result.succeeded)
        assertIs<Success<TxnEntity>>(result)
        assertEquals(12.34, result.data.amount)
        assertEquals("description", result.data.description)
    }

    @Test
    fun flagTransaction_retrievedTransactionIsFlagged() = runBlockingTest {
        // GIVEN - A new transaction saved in the data store.
        val txn = TxnEntity(transactionId = 1)
        localDataSource.saveTransaction(txn)

        // WHEN - transaction is flagged.
        localDataSource.flagTransaction(txn.transactionId, true)

        // THEN - transaction can be retrieved from local data source and is flagged.
        val result = localDataSource.getTransactionResultById(txn.transactionId)
        assertTrue(result.succeeded)
        assertIs<Success<TxnEntity>>(result)
        assertTrue(result.data.flagged)
    }
}
