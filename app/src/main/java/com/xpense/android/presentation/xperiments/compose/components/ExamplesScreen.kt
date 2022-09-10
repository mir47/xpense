package com.xpense.android.presentation.xperiments.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalMaterialApi
@Preview
@Composable
fun ExamplesScreen(
    modifier: Modifier = Modifier
) {
    val items by remember { mutableStateOf(listOf("item 1", "item 2")) }
//    var currency1 by remember { mutableStateOf("") }
//    var currency2 by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomDropdown(
            modifier = modifier,
            items = items
        ) {
//            currency1 = it
        }

        MaterialDropdown1(
            modifier = modifier,
            items = items
        )

        MaterialDropdown2(
            modifier = modifier,
            items = items
        ) {
//            currency2 = it
        }
    }
}
