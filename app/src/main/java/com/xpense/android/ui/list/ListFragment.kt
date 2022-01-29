package com.xpense.android.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.xpense.android.R
import com.xpense.android.databinding.FragmentListBinding
import com.xpense.android.db.TransactionDatabase

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false)

        val dataSource = TransactionDatabase
            .getInstance(requireActivity().application).transactionDatabaseDao

        val viewModelFactory = ListViewModelFactory(dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)

        binding.viewModel = viewModel

        // Add an Observer on the state variable for navigating when button is pressed.
        viewModel.navigateToDetail.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
                viewModel.doneNavigating()
            }
        }

        val adapter = TransactionAdapter()

        binding.transactionList.adapter = adapter

        viewModel.transactions.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }
}