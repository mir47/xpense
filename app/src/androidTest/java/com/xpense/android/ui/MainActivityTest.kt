package com.xpense.android.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.TxnRepository
import com.xpense.android.util.DataBindingIdlingResource
import com.xpense.android.util.EspressoIdlingResource
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalMaterialApi
@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repository: TxnRepository

    @Before
    fun init() {
        // Not using fake repository
//        repository = ServiceLocator.provideTransactionRepository(
//            ApplicationProvider.getApplicationContext()
//        )

        // use runBlocking because this itself is not a test, so we don't
        // need access to TestCoroutineDispatcher
        runBlocking {
            // clear any previous state from other tests
            repository.deleteAllTransactions()
        }
    }

//    @After
//    fun reset() = ServiceLocator.resetRepository()

    // An idling resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Ignore("example test, does nothing")
    @Test
    fun templateTest() = runBlocking {
        // Set initial state.
        repository.saveTransaction(Txn(id = 1))

        // Start up Transactions screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Espresso code goes here.
        // ...

        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

    @Ignore("Update for compose navigation testing")
    @Test
    fun editTransaction() = runBlocking {
        // Set initial state.
        repository.saveTransaction(
            Txn(
                id = 1,
                amount = 12.34,
                description = "description"
            )
        )

        // Start up Transactions screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
//        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on the transaction on the list
        composeTestRule
            .onNodeWithText("R 12.34")
            .assertExists()
        composeTestRule
            .onNodeWithText("description")
            .assertExists()
            .performClick()

        // Check that all the data is correct, and edit the data
        composeTestRule
            .onNodeWithText("12.34")
            .assertExists()
            .performTextReplacement("21.43")
        composeTestRule
            .onNodeWithText("description")
            .assertExists()
            .performTextReplacement("new description")

        // Click on the save button
        composeTestRule
            .onNodeWithText("save", ignoreCase = true)
            .performClick()

        // Verify transaction is displayed on screen in the transaction list.
        composeTestRule
            .onNodeWithText("R 21.43")
            .assertExists()

        // Verify previous transaction is not displayed.
        composeTestRule
            .onNodeWithText("R 12.34")
            .assertDoesNotExist()

        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

    @Ignore("TODO: implement")
    @Test
    fun createOneTransaction_deleteTransaction() {

        // 1. Start TasksActivity.

        // 2. Add an active task by clicking on the FAB and saving a new task.

        // 3. Open the new task in a details view.

        // 4. Click delete task in menu.

        // 5. Verify it was deleted.

        // 6. Make sure the activity is closed.

    }
}
