package com.xpense.android.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.xpense.android.R
import com.xpense.android.databinding.FragmentTransactionBinding
import com.xpense.android.data.local.TransactionDatabase

class TransactionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTransactionBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_transaction, container, false)

        val dataSource = TransactionDatabase
            .getInstance(requireActivity().application).transactionDao

        val viewModelFactory = TransactionViewModelFactory(dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(TransactionViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.navigateExit.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                viewModel.doneNavigating()
            }
        }

        return binding.root
    }
}