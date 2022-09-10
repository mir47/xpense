package com.xpense.android.presentation.xperiments.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.toSize

/**
 * Custom dropdown menu
 */
@Composable
fun CustomDropdown(
    modifier: Modifier = Modifier,
    label: String = "CustomDropdown",
    items: List<String>,
    onItemSelect: (item: String) -> Unit
) {

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var expanded by remember { mutableStateOf(false) }

    // Create a string value to store the selected option
    var selectedText by remember { mutableStateOf("init") }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

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
            label = { Text(text = label) },
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )

//        val focusManager = LocalFocusManager.current

        // Create a drop-down menu with list of options,
        // when clicked, set the Text Field text as the selected
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
//                focusManager.clearFocus()
            },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelect(label)
                        selectedText = label
                        expanded = false
//                        focusManager.clearFocus()
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}
