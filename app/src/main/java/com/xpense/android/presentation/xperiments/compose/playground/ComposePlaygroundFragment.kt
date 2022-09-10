package com.xpense.android.presentation.xperiments.compose.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.xpense.android.presentation.ui.theme.XpenseTheme

class ComposePlaygroundFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                XpenseTheme {
                    var clicks by remember { mutableStateOf(0) }

                    Column {
                        var selectAll by remember { mutableStateOf(false) }

                        MyCheckbox(
                            selectAll = selectAll,
                            onSelectAll = { selectAll = it }
                        )

                        ClickCounter(clicks) { clicks++ }

                        var nameHeader by remember { mutableStateOf("New Header") }
                        NamePicker(
                            header = nameHeader,
                            names = listOf("Bill", "Linus", "Elon", "Jobs"),
                            onNameClicked = { nameHeader = it }
                        )
                    }
                }
            }
        }
    }
}
