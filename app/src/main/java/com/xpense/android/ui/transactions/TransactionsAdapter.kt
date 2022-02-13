package com.xpense.android.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xpense.android.databinding.ItemTransactionBinding
import com.xpense.android.data.Transaction

/**
 * Adapter for transactions
 */
class TransactionsAdapter(private val clickListener: TransactionListener):
    ListAdapter<Transaction, TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionViewHolder.from(parent)
}

/**
 * ViewHolder for a transaction item
 */
class TransactionViewHolder private constructor(private val binding: ItemTransactionBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Transaction, clickListener: TransactionListener) {
        binding.transaction = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup) = TransactionViewHolder(ItemTransactionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between
 * and old list and a new list that's been passed to `submitList`.
 */
class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
        oldItem.transactionId == newItem.transactionId

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
        oldItem == newItem
}

/**
 * Item click listener
 */
class TransactionListener(val clickListener: (transactionId: Long) -> Unit) {
    fun onClick(transaction: Transaction) = clickListener(transaction.transactionId)
}
