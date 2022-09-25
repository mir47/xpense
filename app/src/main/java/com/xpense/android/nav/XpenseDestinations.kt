package com.xpense.android.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Contract for information needed on every Xpense navigation destination
 */
interface XpenseDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Xpense app navigation destinations
 */
object TxnList : XpenseDestination {
    override val icon = Icons.Filled.MoneyOff
    override val route = "txn_list"
}

object TxnAddEdit : XpenseDestination {
    override val icon = Icons.Filled.PieChart
    override val route = "txn_add_edit"
}

// Screens to be displayed in the top XpenseTabRow
val xpenseTabRowScreens = listOf(TxnList, TxnAddEdit)
