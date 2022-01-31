package com.xpense.android.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xpense.android.R
import com.xpense.android.XpenseApplication
import com.xpense.android.databinding.FragmentMainBinding
import com.xpense.android.data.local.TransactionDatabase

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)

        val dataSource = TransactionDatabase
            .getInstance(requireActivity().application).transactionDao
//        val dataSource = (requireContext().applicationContext as XpenseApplication).transactionRepository

        val viewModelFactory = MainViewModelFactory(dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        // Add an Observer on the state variable for navigating when button is pressed.
        viewModel.navigateToTransaction.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(MainFragmentDirections
                    .actionMainFragmentToTransactionFragment())
                viewModel.doneNavigating()
            }
        }

        val transactionAdapter = TransactionAdapter()
        binding.transactionList.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.transactions.observe(viewLifecycleOwner) {
            it?.let {
                transactionAdapter.submitList(it)
            }
        }

        return binding.root
    }
}