package com.xpense.android.presentation.txn_add_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xpense.android.XpenseApplication
import com.xpense.android.databinding.FragmentTxnAddEditBinding

class TxnAddEditFragment : Fragment() {

    private val _viewModelAddEdit by viewModels<TxnAddEditViewModel> {
        TxnAddEditViewModel.TxnAddEditViewModelFactory(
            TxnAddEditFragmentArgs.fromBundle(requireArguments()).transactionId,
            TxnAddEditFragmentArgs.fromBundle(requireArguments()).sms,
            (requireContext().applicationContext as XpenseApplication).txnRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTxnAddEditBinding.inflate(inflater)

        binding.viewModel = _viewModelAddEdit

        // Make data binding lifecycle aware, to automatically update layout with LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        _viewModelAddEdit.navigateExit.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                _viewModelAddEdit.doneNavigating()
            }
        }

        return binding.root
    }
}
