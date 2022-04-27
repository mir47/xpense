package com.xpense.android.presentation.ui.experiments.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xpense.android.databinding.FragmentMotionClickBinding

class MotionClickFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMotionClickBinding.inflate(inflater)
        return binding.root
    }
}
