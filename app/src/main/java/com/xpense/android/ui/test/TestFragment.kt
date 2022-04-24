package com.xpense.android.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xpense.android.R
import com.xpense.android.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTestBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_test, container, false)

        binding.button1.setOnClickListener {
            findNavController().navigate(TestFragmentDirections
                .actionTestFragmentToTransactionFragment())
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(TestFragmentDirections
                .actionTestFragmentToTesttFragment())
        }

        return binding.root
    }
}