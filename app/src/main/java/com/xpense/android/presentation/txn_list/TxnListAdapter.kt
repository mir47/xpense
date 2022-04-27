package com.xpense.android.presentation.txn_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xpense.android.databinding.ItemTxnBinding
import com.xpense.android.data.source.local.model.TxnEntity

/**
 * Adapter for transactions
 */
class TxnListAdapter(private val clickListener: TransactionListener):
    ListAdapter<TxnEntity, TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionViewHolder.from(parent)
}

/**
 * ViewHolder for a transaction item
 */
class TransactionViewHolder private constructor(private val binding: ItemTxnBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(txnEntity: TxnEntity, clickListener: TransactionListener) {
        binding.txnEntity = txnEntity
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup) = TransactionViewHolder(ItemTxnBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between
 * and old list and a new list that's been passed to `submitList`.
 */
class TransactionDiffCallback : DiffUtil.ItemCallback<TxnEntity>() {
    override fun areItemsTheSame(oldItem: TxnEntity, newItem: TxnEntity) =
        oldItem.transactionId == newItem.transactionId

    override fun areContentsTheSame(oldItem: TxnEntity, newItem: TxnEntity) =
        oldItem == newItem
}

/**
 * Item click listener
 */
class TransactionListener(val clickListener: (transactionId: Long) -> Unit) {
    fun onClick(txnEntity: TxnEntity) = clickListener(txnEntity.transactionId)
}
