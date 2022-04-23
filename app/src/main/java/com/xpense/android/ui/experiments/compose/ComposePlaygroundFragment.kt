package com.xpense.android.ui.experiments.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.xpense.android.ui.theme.XpenseTheme

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

@Composable
fun ClickCounter(
    clicks: Int,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}

@Composable
fun MyCheckbox(
    selectAll: Boolean,
    onSelectAll: (Boolean) -> Unit
) {
    Checkbox(
        checked = selectAll,
        onCheckedChange = onSelectAll
    )
}

/**
 * Display a list of names the user can click with a header
 */
@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    onNameClicked: (String) -> Unit
) {
    Column {
        // this will recompose when [header] changes, but not when [names] changes
        Text(header, style = MaterialTheme.typography.h5)
        Divider()

        // LazyColumn is the Compose version of a RecyclerView.
        // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
        LazyColumn {
            items(names) { name ->
                // When an item's [name] updates, the adapter for that item
                // will recompose. This will not recompose when [header] changes
                NamePickerItem(name, onNameClicked)
            }
        }
    }
}

/**
 * Display a single name the user can click.
 */
@Composable
private fun NamePickerItem(name: String, onClicked: (String) -> Unit) {
    Text(name, Modifier.clickable(onClick = { onClicked(name) }))
}
