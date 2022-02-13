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

        // Make data binding lifecycle aware, to automatically update layout with LiveData
        binding.lifecycleOwner = this

        // Add an Observer on the state variable for navigating when button is pressed.
        _viewModel.navigateToCreateTransaction.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    MainFragmentDirections
                        .actionMainFragmentToTransactionFragment()
                )
                _viewModel.doneNavigating()
            }
        }

        val transactionAdapter = TransactionAdapter(TransactionListener { transactionId ->
            // TODO: click logic should be handled in the view model, with navigation event sent to fragment via LiveData
            findNavController().navigate(
                MainFragmentDirections
                    .actionMainFragmentToTransactionFragment()
                    .setTransactionId(transactionId)
            )
        })

        binding.transactionList.adapter = transactionAdapter

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
