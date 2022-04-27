package com.xpense.android.presentation.xperiments.androidviewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xpense.android.databinding.FragmentMyAndroidViewModelBinding

class MyAndroidViewModelFragment : Fragment() {

    private val _viewModel by viewModels<MyAndroidViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMyAndroidViewModelBinding.inflate(inflater)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
