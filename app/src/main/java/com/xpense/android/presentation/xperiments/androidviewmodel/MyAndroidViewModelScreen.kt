package com.xpense.android.presentation.xperiments.androidviewmodel

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyAndroidViewModelScreen(
    vm: MyAndroidViewModel
) {
    Surface {
        Text(
            modifier = Modifier.padding(16.dp),
            text = vm.text.value
            // from xml: android:textAppearance="@style/TextAppearance.AppCompat.Large"
        )
    }
}
