package com.xpense.android.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the Transaction class with Room.
 */
@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param transaction new value to write
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(transaction: Transaction)

    /**
     * Selects and returns the row that matches the supplied id, which is our key.
     *
     * @param key transactionId to match
     */
    @Query("SELECT * from transaction_table WHERE transaction_id = :key")
    suspend fun get(key: Long): Transaction?

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by id in descending order.
     */
    @Query("SELECT * FROM transaction_table ORDER BY transaction_id DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    /**
     * Selects and returns an observable transaction with given transactionId.
     */
    @Query("SELECT * from transaction_table WHERE transaction_id = :key")
    fun observeTransactionWithId(key: Long): LiveData<Transaction>

    /**
     * Selects and returns the transaction with given transactionId.
     */
    @Query("SELECT * from transaction_table WHERE transaction_id = :key")
    suspend fun getTransactionWithId(key: Long): Transaction?

    /**
     * Selects and returns the last inserted transaction.
     */
    @Query("SELECT * from transaction_table ORDER BY transaction_id DESC LIMIT 1")
    suspend fun getLastTransaction(): Transaction?

    /**
     * Delete all transactions.
     */
    @Query("DELETE from transaction_table")
    fun clear()
}
