package com.xpense.android.ui.txn.add_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.xpense.android.ui.components.MaterialDropdown2

@ExperimentalMaterialApi
@Composable
fun TxnAddEditScreen(
    vm: TxnAddEditViewModel,
    onDone: () -> Unit,
) {
    val state by vm.uiState

    when (state) {
        is UiState.Default -> {}
        is UiState.Loading -> {}
        is UiState.Error -> {}
        is UiState.Done -> { onDone() }
    }

    Surface {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = vm.amount.value,
                onValueChange = {
                    vm.amount.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Amount") } // replace hard coded string with R.string.amount
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = vm.description.value,
                onValueChange = {
                    vm.description.value = it
                },
                label = { Text("Description") } // replace hard coded string with R.string.description
            )

            MaterialDropdown2(
                modifier = Modifier.padding(vertical = 4.dp),
                label = "Type",
                items = vm.items.value
            ) { }

            Button(
                modifier = Modifier.padding(vertical = 4.dp),
                onClick = { vm.submit() }
            ) {
                Text(text = "SAVE") // replace hard coded string with R.string.save
            }
        }
    }
}
