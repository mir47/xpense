package com.xpense.android.presentation.xperiments.androidviewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xpense.android.presentation.ui.theme.XpenseTheme

class MyAndroidViewModelFragment : Fragment() {

    private val vm by viewModels<MyAndroidViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                XpenseTheme {
                    MyAndroidViewModelScreen(vm)
                }
            }
        }
    }
}
