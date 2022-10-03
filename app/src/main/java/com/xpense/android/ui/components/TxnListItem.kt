package com.xpense.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = getFormattedDateString(txn.createdTimestamp),
                    style = typography.caption,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = txn.description,
                    style = typography.body1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.amount_format_example, txn.amount),
                    style = typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}
