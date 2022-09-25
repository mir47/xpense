package com.xpense.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.xpense.android.di.AppContainer
import com.xpense.android.nav.TxnList
import com.xpense.android.nav.XpenseNavHost
import com.xpense.android.nav.navigateSingleTopTo
import com.xpense.android.nav.xpenseTabRowScreens
import com.xpense.android.ui.components.XpenseTabRow
import com.xpense.android.presentation.ui.theme.XpenseTheme

@ExperimentalMaterialApi
@Composable
fun XpenseApp(
    appContainer: AppContainer,
) {
    XpenseTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = xpenseTabRowScreens.find { it.route == currentDestination?.route } ?: TxnList

        Scaffold(
            topBar = {
                XpenseTabRow(
                    allScreens = xpenseTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            XpenseNavHost(
                appContainer = appContainer,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
