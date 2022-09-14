package com.xpense.android.presentation.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xpense.android.R

@Composable
fun AboutScreen() {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.about_android),
                contentDescription = stringResource(id = R.string.green_question_mark)
            )
            Text(
                text = stringResource(id = R.string.about_text)
            )
        }
    }
}
