package com.xpense.android.ui.addoredittransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xpense.android.R
import com.xpense.android.databinding.FragmentAddEditTransactionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddEditTransactionFragment : Fragment() {

    // use Koin to retrieve the ViewModel instance
    private val _viewModelAddEdit: AddEditTransactionViewModel by viewModel {
        val args = AddEditTransactionFragmentArgs.fromBundle(requireArguments())
        parametersOf(args.transactionId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAddEditTransactionBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_edit_transaction, container, false)

        binding.viewModel = _viewModelAddEdit

        // Make data binding lifecycle aware, to automatically update layout with LiveData
        binding.lifecycleOwner = this

        _viewModelAddEdit.navigateExit.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                _viewModelAddEdit.doneNavigating()
            }
        }

        return binding.root
    }
}
