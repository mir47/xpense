package com.xpense.android.presentation.legacy.txn_list

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
import com.xpense.android.databinding.FragmentLegacyTxnListBinding

class LegacyTxnListFragment : Fragment() {

    private val _viewModel by viewModels<LegacyTxnListViewModel> {
        LegacyTxnListViewModel.TxnListViewModelFactory(
            (requireContext().applicationContext as XpenseApplication).txnRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLegacyTxnListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_legacy_txn_list, container, false)

        binding.viewModel = _viewModel

        // Make data binding lifecycle aware, to automatically update layout with LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        // Add an Observer on the state variable for navigating when button is pressed.
        _viewModel.navigateToTxnAddEdit.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    LegacyTxnListFragmentDirections.actionLegacyTxnListFragmentToTxnAddEditFragment()
                )
                _viewModel.doneNavigating()
            }
        }

        val txnListAdapter = TxnListAdapter(TxnListener { txnId ->
            // TODO: click logic should be handled in the view model, with navigation event sent to fragment via LiveData
            findNavController().navigate(
                LegacyTxnListFragmentDirections.actionLegacyTxnListFragmentToTxnAddEditFragment()
                    .setTransactionId(txnId)
            )
        })

        binding.transactionList.adapter = txnListAdapter

        _viewModel.transactions.observe(viewLifecycleOwner) {
            if (it is Success) {
                txnListAdapter.submitList(it.data)
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
