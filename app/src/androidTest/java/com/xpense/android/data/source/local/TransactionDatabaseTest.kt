package com.xpense.android.data.source.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.xpense.android.data.TxnEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TransactionDatabaseTest {

    private lateinit var dao: TransactionDao
    private lateinit var db: TransactionDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here
        // disappears when the process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.transactionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTransaction() = runBlocking {
        dao.insert(TxnEntity(amount = 1.23))
        assertEquals(1.23, dao.get(key = 1)?.amount)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteTransaction() = runBlocking {
        dao.insert(TxnEntity(amount = 2.34))
        dao.insert(TxnEntity(amount = 3.45))
        assertEquals(2.34, dao.get(key = 1)?.amount)
        assertEquals(3.45, dao.get(key = 2)?.amount)
    }
}
