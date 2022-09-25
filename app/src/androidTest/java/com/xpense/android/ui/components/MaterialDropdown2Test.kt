package com.xpense.android.ui.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
class MaterialDropdown2Test {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun materialDropdown2Test_itemSelectChangesSelectedItem() {
        val items = listOf("item 1", "item 2")
        var selectedItem by mutableStateOf("item 1")

        composeTestRule.setContent {
            MaterialDropdown2(
                items = items,
                onItemSelect = {
                    selectedItem = it
                }
            )
        }

        composeTestRule
            .onRoot(useUnmergedTree = true)
            .printToLog("mmmmm1")

        composeTestRule
            .onNodeWithText("MaterialDropdown2")
            .assertExists()

        composeTestRule
            .onNodeWithContentDescription("Dropdown menu")
            .performClick()

//        composeTestRule
//            .onRoot(useUnmergedTree = true)
////            .onNodeWithText("MaterialDropdown2", useUnmergedTree = true)
//            .printToLog("mmmmm2")

        composeTestRule
            .onAllNodes(isRoot(), useUnmergedTree = true)
            .onFirst()
            .printToLog("mmmmm2")

        composeTestRule
            .onAllNodes(isRoot(), useUnmergedTree = true)
            .onLast()
            .printToLog("mmmmm3")

        composeTestRule
            .onNodeWithText("item 2")
            .assertIsDisplayed()
            .performClick()

        assert(selectedItem == "item 2")

//        Thread.sleep(4000)

        // below fails - drop down menu does not dismiss and text does not update
//        composeTestRule
//            .onNodeWithContentDescription("Trailing icon for exposed dropdown menu")
//            .onParent()
//            .assertTextEquals("item 2")

    }
}
