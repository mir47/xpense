package com.xpense.android.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.xpense.android.R
import com.xpense.android.XpenseApplication
import com.xpense.android.data.Result.Success
import com.xpense.android.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {

    private val _viewModel by viewModels<TransactionsViewModel> {
        TransactionsViewModel.TransactionsViewModelFactory(
            (requireContext().applicationContext as XpenseApplication).transactionRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTransactionsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_transactions, container, false)

        binding.viewModel = _viewModel

        // Make data binding lifecycle aware, to automatically update layout with LiveData
        binding.lifecycleOwner = this

        // Add an Observer on the state variable for navigating when button is pressed.
        _viewModel.navigateToCreateTransaction.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    TransactionsFragmentDirections
                        .actionMainFragmentToTransactionFragment()
                )
                _viewModel.doneNavigating()
            }
        }

        val transactionsAdapter = TransactionsAdapter(TransactionListener { transactionId ->
            // TODO: click logic should be handled in the view model, with navigation event sent to fragment via LiveData
            findNavController().navigate(
                TransactionsFragmentDirections
                    .actionMainFragmentToTransactionFragment()
                    .setTransactionId(transactionId)
            )
        })

        binding.transactionList.adapter = transactionsAdapter

        _viewModel.transactions.observe(viewLifecycleOwner) {
            if (it is Success) {
                transactionsAdapter.submitList(it.data)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = super.onOptionsItemSelected(item)
            || NavigationUI.onNavDestinationSelected(item, findNavController())
}
