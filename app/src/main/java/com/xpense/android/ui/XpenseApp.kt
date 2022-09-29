package com.xpense.android.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xpense.android.nav.XpenseNavHost
import com.xpense.android.presentation.ui.theme.XpenseTheme

@ExperimentalMaterialApi
@Composable
fun XpenseApp() {
    XpenseTheme {
        val navController = rememberNavController()
        var canPop by remember { mutableStateOf(false) }

        DisposableEffect(navController) {
            val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
                canPop = controller.previousBackStackEntry != null
            }
            navController.addOnDestinationChangedListener(listener)
            onDispose {
                navController.removeOnDestinationChangedListener(listener)
            }
        }

        val navigationIcon: (@Composable () -> Unit)? =
            if (canPop) { {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "backIcon"
                    )
                }
            } } else { null }

        var appBarState by remember { mutableStateOf(AppBarState()) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = appBarState.title)
                    },
                    actions = {
                        appBarState.actions?.invoke(this)
                    },
                    navigationIcon = navigationIcon,
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                    elevation = 10.dp,
                )
            },
            content = {
                XpenseNavHost(
                    navController = navController,
                    onComposing = {
                        appBarState = it
                    }
                )
            }
        )
    }
}

data class AppBarState(
    val title: String = "",
    val actions: (@Composable RowScope.() -> Unit)? = null
)
