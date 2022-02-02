package com.xpense.android.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xpense.android.R
import com.xpense.android.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    // use Koin to retrieve the ViewModel instance
    private val _viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)

        binding.viewModel = _viewModel

        binding.lifecycleOwner = this

        // Add an Observer on the state variable for navigating when button is pressed.
        _viewModel.navigateToTransaction.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(MainFragmentDirections
                    .actionMainFragmentToTransactionFragment())
                _viewModel.doneNavigating()
            }
        }

        val transactionAdapter = TransactionAdapter()
        binding.transactionList.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }

        _viewModel.transactions.observe(viewLifecycleOwner) {
            it?.let {
                transactionAdapter.submitList(it)
            }
        }

        return binding.root
    }
}