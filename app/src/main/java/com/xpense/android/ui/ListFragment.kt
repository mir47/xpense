package com.xpense.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xpense.android.R
import com.xpense.android.databinding.FragmentListBinding

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false)

        val viewModel: ListViewModel by viewModels()
        binding.viewModel = viewModel

        // Add an Observer on the state variable for navigating when button is pressed.
        viewModel.navigateToDetail.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
                viewModel.doneNavigating()
            }
        }

        return binding.root
    }
}