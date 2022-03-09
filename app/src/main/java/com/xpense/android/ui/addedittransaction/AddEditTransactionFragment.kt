package com.xpense.android.ui.addedittransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xpense.android.XpenseApplication
import com.xpense.android.databinding.FragmentAddEditTransactionBinding

class AddEditTransactionFragment : Fragment() {

    private val _viewModelAddEdit by viewModels<AddEditTransactionViewModel> {
        AddEditTransactionViewModel.AddEditTransactionViewModelFactory(
            AddEditTransactionFragmentArgs.fromBundle(requireArguments()).transactionId,
            (requireContext().applicationContext as XpenseApplication).transactionRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddEditTransactionBinding.inflate(inflater)

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
