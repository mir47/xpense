package com.xpense.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.xpense.android.R
import com.xpense.android.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)

        return binding.root
    }
}