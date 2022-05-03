package com.xpense.android.presentation.xperiments.compose.txn_add_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.xpense.android.XpenseApplication
import com.xpense.android.presentation.ui.theme.XpenseTheme

@ExperimentalMaterialApi
class TxnAddEditComposeFragment : Fragment() {

    private val _viewModelAddEdit by viewModels<TxnAddEditComposeViewModel> {
        TxnAddEditComposeViewModel.TxnAddEditComposeViewModelFactory(
            TxnAddEditComposeFragmentArgs.fromBundle(requireArguments()).transactionId,
            (requireContext().applicationContext as XpenseApplication).txnRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewModelAddEdit.navigateExit.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                _viewModelAddEdit.doneNavigating()
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                XpenseTheme {
                    InputForm()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun InputForm() {
    val viewModel: TxnAddEditComposeViewModel = viewModel()

    var amount by remember { mutableStateOf(viewModel.amount) }
    var description by remember { mutableStateOf(viewModel.description) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            value = amount,
            onValueChange = {
                amount = it
                viewModel.amount = it
            },
            label = { Text("Amount") } // replace hard coded string with R.string.amount
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            value = description,
            onValueChange = {
                description = it
                viewModel.description = it
            },
            label = { Text("Description") } // replace hard coded string with R.string.description
        )

        MyCustomDropdown(
            modifier = Modifier.padding(vertical = 4.dp)
        )

        MyMaterialDropdown(
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Button(
            modifier = Modifier.padding(vertical = 4.dp),
            onClick = { viewModel.submit() }
        ) {
            Text(text = "SAVE") // replace hard coded string with R.string.save
        }
    }
}

val items = listOf("Travel", "Groceries", "Rent")

/**
 * Custom dropdown menu
 */
@Composable
fun MyCustomDropdown(modifier: Modifier = Modifier) {

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var expanded by remember { mutableStateOf(false) }

    // Create a string value to store the selected option
    var selectedText by remember { mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier = modifier) {
        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text("Type (Custom Dropdown)") },
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )

        // Create a drop-down menu with list of options,
        // when clicked, set the Text Field text as the selected
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = false
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}

/**
 * Use ExposedDropdownMenuBox from Compose material components
 */
@ExperimentalMaterialApi
@Composable
fun MyMaterialDropdown(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            readOnly = true,
            value = items[selectedIndex],
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            label = { Text("Type (Compose Material Dropdown)") } // replace hard coded string with resource
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expanded = false
                    }
                ) {
                    Text(text = item,)
                }
            }
        }
    }
}
