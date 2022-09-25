package com.xpense.android.presentation.txn.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.xpense.android.R
import com.xpense.android.domain.model.Txn
import com.xpense.android.util.getFormattedDateString

@Composable
fun TxnListItem(
    txn: Txn,
    onItemClick: (Txn) -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable { onItemClick(txn) }
            .fillMaxWidth()
            .padding(1.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Green)
        ) {
            Text(
                text = getFormattedDateString(txn.createdTimestamp),
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = txn.description,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(id = R.string.amount_format_example, txn.amount),
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
