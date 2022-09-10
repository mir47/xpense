package com.xpense.android.presentation.txn.compose.add_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xpense.android.presentation.xperiments.compose.components.MaterialDropdown2

@ExperimentalMaterialApi
@Preview
@Composable
fun TxnAddEditScreen() {
    val viewModel: ComposeTxnAddEditViewModel = viewModel()

    var amount by remember { mutableStateOf(viewModel.amount) }
    var description by remember { mutableStateOf(viewModel.description) }
    val items by remember { mutableStateOf(viewModel.items) }

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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
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

        MaterialDropdown2(
            modifier = Modifier.padding(vertical = 4.dp),
            label = "Type",
            items = items
        ) { }

        Button(
            modifier = Modifier.padding(vertical = 4.dp),
            onClick = { viewModel.submit() }
        ) {
            Text(text = "SAVE") // replace hard coded string with R.string.save
        }
    }
}
