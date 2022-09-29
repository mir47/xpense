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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Filter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xpense.android.domain.model.Txn
import com.xpense.android.ui.AppBarState
import com.xpense.android.ui.components.TxnListItem

@ExperimentalMaterialApi
@Composable
fun TxnListScreen(
    vm: TxnListViewModel,
    onComposing: (AppBarState) -> Unit,
    onItemClick: (Txn) -> Unit,
    onFabClick: () -> Unit,
) {

        val state by vm.state

        LaunchedEffect(key1 = true) {
            onComposing(
                AppBarState(
                    title = "Xpense",
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Filter,
                                contentDescription = null
                            )
                        }
                    }
                )
            )
        }

//    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics {
                    contentDescription = "Transaction List Screen"
                }
        ) {
            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Error -> {
                    Text(
                        text = (state as UiState.Error).message,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                is UiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items((state as UiState.Success).txnsData) { txn ->
                            TxnListItem(
                                txn = txn,
                                onItemClick = {
                                    onItemClick(txn)
                                }
                            )
                        }
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = { onFabClick() }
            ) {
                Image(
                    painter = painterResource(android.R.drawable.ic_input_add),
                    contentDescription = "FAB"
                )
            }

//            if (state.error.isNotBlank()) {
//                Text(
//                    text = state.error,
//                    color = MaterialTheme.colors.error,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                        .align(Alignment.Center)
//                )
//            }
//            if (state.isLoading) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
        }
//    }
}
