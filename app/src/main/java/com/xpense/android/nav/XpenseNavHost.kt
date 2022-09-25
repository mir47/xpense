package com.xpense.android.nav

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xpense.android.di.AppContainer
import com.xpense.android.presentation.txn.add_edit.TxnAddEditScreen
import com.xpense.android.presentation.txn.add_edit.TxnAddEditViewModel
import com.xpense.android.presentation.txn.list.TxnListScreen
import com.xpense.android.presentation.txn.list.TxnListViewModel

@ExperimentalMaterialApi
@Composable
fun XpenseNavHost(
    appContainer: AppContainer,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = TxnList.route,
        modifier = modifier
    ) {
        composable(route = TxnList.route) {
            val vm: TxnListViewModel = viewModel(
                factory = TxnListViewModel.provideFactory(appContainer.getTxnsUseCase)
            )
            TxnListScreen(vm)
        }
        composable(route = TxnAddEdit.route) {
            val vm: TxnAddEditViewModel = viewModel(
                factory = TxnAddEditViewModel.provideFactory(0L, appContainer.txnRepository)
            )
            TxnAddEditScreen(vm)
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
