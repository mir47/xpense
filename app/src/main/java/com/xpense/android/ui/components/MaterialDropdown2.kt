package com.xpense.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Use ExposedDropdownMenuBox from Compose material components
 */
@ExperimentalMaterialApi
@Composable
fun MaterialDropdown2(
    modifier: Modifier = Modifier,
    label: String = "MaterialDropdown2",
    items: List<String>,
    onItemSelect: (item: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

//    val focusManager = LocalFocusManager.current

    // bug in material library 1.3.0 - label gets cut
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = items[selectedIndex],
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
//            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            label = { Text(text = label) },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
//                focusManager.clearFocus()
            }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expanded = false
//                        focusManager.clearFocus()
                        onItemSelect(items[index])
                    }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}
