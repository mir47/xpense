package com.xpense.android.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xpense.android.databinding.ListItemTransactionBinding
import com.xpense.android.db.Transaction

class TransactionAdapter : ListAdapter<Transaction, RecyclerView.ViewHolder>(TransactionDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as Transaction
        (holder as TransactionViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TransactionViewHolder.from(parent)
    }

    class TransactionViewHolder private constructor(private val binding: ListItemTransactionBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Transaction) {
            binding.transaction = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TransactionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTransactionBinding.inflate(layoutInflater, parent, false)
                return TransactionViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between
 * and old list and a new list that's been passed to `submitList`.
 */
class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.transactionId == newItem.transactionId
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}
