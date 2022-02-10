package com.xpense.android.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.xpense.android.R
import com.xpense.android.data.local.Transaction
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
        _viewModel.navigateToCreateTransaction.observe(viewLifecycleOwner) {
            if (it) {
                // empty object to imply the creation of a new Transaction
                val transaction = Transaction()
                findNavController().navigate(MainFragmentDirections
                    .actionMainFragmentToTransactionFragment(transaction))
                _viewModel.doneNavigating()
            }
        }

        val transactionAdapter = TransactionAdapter()
        binding.transactionList.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }

        _viewModel.transactions.observe(viewLifecycleOwner) {
            it?.let { transactionAdapter.submitList(it) }
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
