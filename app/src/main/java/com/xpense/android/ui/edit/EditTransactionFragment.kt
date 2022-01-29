package com.xpense.android.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xpense.android.R
import com.xpense.android.databinding.FragmentEditTransactionBinding
import com.xpense.android.db.TransactionDatabase

class EditTransactionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentEditTransactionBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_transaction, container, false)

        val dataSource = TransactionDatabase
            .getInstance(requireActivity().application).transactionDatabaseDao

        val viewModelFactory = EditTransactionViewModelFactory(dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(EditTransactionViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root
    }
}