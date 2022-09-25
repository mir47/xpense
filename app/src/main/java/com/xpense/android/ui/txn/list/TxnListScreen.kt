package com.xpense.android.ui.txn.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xpense.android.presentation.txn.list.components.TxnListItem

@ExperimentalMaterialApi
@Composable
fun TxnListScreen(
    vm: TxnListViewModel
) {

        val state by vm.state

//    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics {
                    contentDescription = "Transaction List Screen"
                }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.txns) { txn ->
                    TxnListItem(
                        txn = txn,
                        onItemClick = {
//                            navController.navigate(
//                                TxnListFragmentDirections
//                                    .actionTxnListFragmentToTxnAddEditFragment()
//                                    .setTransactionId(txn.id)
//                            )
                        }
                    )
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = {
//                    navController.navigate(
//                        TxnListFragmentDirections
//                            .actionTxnListFragmentToTxnAddEditFragment()
//                    )
                }
            ) {
                Image(
                    painter = painterResource(android.R.drawable.ic_input_add),
                    contentDescription = "FAB"
                )
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
//    }
}
