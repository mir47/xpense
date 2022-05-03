package com.xpense.android.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xpense.android.data.source.local.model.TxnEntity

/**
 * Defines methods for using the Transaction class with Room.
 */
@Dao
interface TxnDao {

    @Insert
    suspend fun insert(txnEntity: TxnEntity)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param txnEntity new value to write
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(txnEntity: TxnEntity)

    /**
     * Update the flagged status of a transaction
     *
     * @param transactionId id of the transaction
     * @param flagged       status to be updated
     */
    @Query("UPDATE txn_table SET flagged = :flagged WHERE transaction_id = :transactionId")
    suspend fun updateFlagged(transactionId: Long, flagged: Boolean)

    /**
     * Selects and returns the row that matches the supplied id, which is our key.
     *
     * @param key transactionId to match
     */
    @Query("SELECT * from txn_table WHERE transaction_id = :key")
    suspend fun get(key: Long): TxnEntity?

    /**
     * Selects and returns all rows in the table,
     * sorted by id in descending order.
     */
    // TODO: Migrate LiveData to Kotlin Flow
    @Query("SELECT * FROM txn_table ORDER BY transaction_id DESC")
    fun observeTransactions(): LiveData<List<TxnEntity>>

    /**
     * Selects and returns the transaction with given transactionId.
     */
    @Query("SELECT * from txn_table WHERE transaction_id = :key")
    suspend fun getTransactionWithId(key: Long): TxnEntity?

    /**
     * Selects and returns all rows in the table,
     * sorted by id in descending order.
     */
    @Query("SELECT * from txn_table ORDER BY transaction_id DESC")
    suspend fun getAllTransactions(): List<TxnEntity>

    /**
     * Selects and returns the last inserted transaction.
     */
    @Query("SELECT * from txn_table ORDER BY transaction_id DESC LIMIT 1")
    suspend fun getLastTransaction(): TxnEntity?

    /**
     * Delete all transactions.
     */
    @Query("DELETE from txn_table")
    fun clear()
}
