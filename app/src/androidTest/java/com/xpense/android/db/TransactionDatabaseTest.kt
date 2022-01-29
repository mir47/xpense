package com.xpense.android.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TransactionDatabaseTest {

    private lateinit var dao: TransactionDatabaseDao
    private lateinit var db: TransactionDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java)
                // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.transactionDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTransaction() = runBlocking {
        val transaction = Transaction(transactionId = 123, amount = 1.23)
        dao.insert(transaction)
        val getTransaction = dao.get(123)
        assertEquals(1.23, getTransaction?.amount)
    }
}

