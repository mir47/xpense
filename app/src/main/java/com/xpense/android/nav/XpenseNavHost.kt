package com.xpense.android.nav

import android.app.Activity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xpense.android.di.ViewModelFactoryProvider
import com.xpense.android.ui.AppBarState
import com.xpense.android.ui.txn.add_edit.TxnAddEditScreen
import com.xpense.android.ui.txn.add_edit.TxnAddEditViewModel
import com.xpense.android.ui.txn.list.TxnListScreen
import com.xpense.android.ui.txn.list.TxnListViewModel
import dagger.hilt.android.EntryPointAccessors

@ExperimentalMaterialApi
@Composable
fun XpenseNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    txnListViewModel: TxnListViewModel = viewModel(), // Hilt constructor injection
    onComposing: (AppBarState) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = TxnList.route,
        modifier = modifier
    ) {
        composable(route = TxnList.route) {
            TxnListScreen(
                vm = txnListViewModel,
                onComposing = {
                    onComposing(it)
                },
                onItemClick = {
                    navController.navigateSingleTopTo(TxnAddEdit.route)
                },
                onFabClick = {
                    navController.navigateSingleTopTo(TxnAddEdit.route)
                }
            )
        }
        composable(route = TxnAddEdit.route) {
            // Hilt assisted injection using factory
            val vm: TxnAddEditViewModel = txnAddEditViewModel(0L)
            TxnAddEditScreen(
                vm = vm,
                onComposing = {
                    onComposing(it)
                },
                onDone = {
                    navController.navigateUp()
                }
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

@Composable
fun txnAddEditViewModel(txnId: Long): TxnAddEditViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).txnAddEditViewModelFactory()

    return viewModel(factory = TxnAddEditViewModel.provideFactory(factory, txnId))
}
