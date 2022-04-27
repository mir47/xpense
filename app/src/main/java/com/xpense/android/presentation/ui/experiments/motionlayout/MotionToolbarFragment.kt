package com.xpense.android.presentation.ui.experiments.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.xpense.android.databinding.FragmentMotionToolbarBinding

class MotionToolbarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMotionToolbarBinding.inflate(inflater)
        val appBarLayout: AppBarLayout = binding.appbarLayout
        val motionLayout: MotionLayout = binding.motionLayout
        coordinateMotion(appBarLayout, motionLayout)
        return binding.root
    }

    private fun coordinateMotion(appBarLayout: AppBarLayout, motionLayout: MotionLayout) {
        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }
        appBarLayout.addOnOffsetChangedListener(listener)
    }
}
