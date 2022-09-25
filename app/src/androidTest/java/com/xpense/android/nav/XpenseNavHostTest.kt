package com.xpense.android.nav

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.xpense.android.launchXpenseApp
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalMaterialApi
@LargeTest
@RunWith(AndroidJUnit4::class)
class XpenseNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            // Creates a TestNavHostController
            navController = TestNavHostController(LocalContext.current)

            // Sets a ComposeNavigator to the navController so it can navigate through composables
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            // Using targetContext as the Context of the instrumentation code
            composeTestRule.launchXpenseApp(ApplicationProvider.getApplicationContext())
        }
    }

    @Ignore("Update for compose navigation testing")
    @Test
    fun verifyTransactionListStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Transaction List Screen")
            .assertIsDisplayed()
    }
}
