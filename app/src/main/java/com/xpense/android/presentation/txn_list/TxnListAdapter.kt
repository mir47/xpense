package com.xpense.android.presentation.txn_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xpense.android.databinding.ItemTxnBinding
import com.xpense.android.domain.model.Txn

/**
 * Adapter for transactions
 */
class TxnListAdapter(private val clickListener: TxnListener):
    ListAdapter<Txn, TxnViewHolder>(TxnDiffCallback()) {

    override fun onBindViewHolder(holder: TxnViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TxnViewHolder.from(parent)
}

/**
 * ViewHolder for a transaction item
 */
class TxnViewHolder private constructor(private val binding: ItemTxnBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(txn: Txn, clickListener: TxnListener) {
        binding.txn = txn
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup) = TxnViewHolder(ItemTxnBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between
 * and old list and a new list that's been passed to `submitList`.
 */
class TxnDiffCallback : DiffUtil.ItemCallback<Txn>() {
    override fun areItemsTheSame(oldItem: Txn, newItem: Txn) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Txn, newItem: Txn) =
        oldItem == newItem
}

/**
 * Item click listener
 */
class TxnListener(val clickListener: (txnId: Long) -> Unit) {
    fun onClick(txn: Txn) = clickListener(txn.id)
}
