package com.xpense.android.nav

/**
 * Contract for information needed on every navigation destination
 */
interface XpenseDestination {
    val route: String
}

/**
 * App navigation destinations
 */
object TxnList : XpenseDestination {
    override val route = "txn_list"
}

object TxnAddEdit : XpenseDestination {
    override val route = "txn_add_edit"
}
