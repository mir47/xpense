package com.xpense.android.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.xpense.android.R
import com.xpense.android.di.ServiceLocator
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.TxnRepository
import com.xpense.android.util.DataBindingIdlingResource
import com.xpense.android.util.EspressoIdlingResource
import com.xpense.android.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var repository: TxnRepository

    @Before
    fun init() {
        // Not using fake repository
        repository = ServiceLocator.provideTransactionRepository(
            ApplicationProvider.getApplicationContext()
        )

        // use runBlocking because this itself is not a test, so we don't
        // need access to TestCoroutineDispatcher
        runBlocking {
            // clear any previous state from other tests
            repository.deleteAllTransactions()
        }
    }

    @After
    fun reset() = ServiceLocator.resetRepository()

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
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on the transaction on the list and verify that all the data is correct.
        onView(withText("R 12.34")).check(matches(isDisplayed()))
        onView(withText("description")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.amount_input)).check(matches(withText("12.34")))
        onView(withId(R.id.description_input)).check(matches(withText("description")))

        // Edit and click on the save button
        onView(withId(R.id.amount_input)).perform(replaceText("21.43"))
        onView(withId(R.id.description_input)).perform(replaceText("new description"))
        onView(withId(R.id.save_button)).perform(click())

        // Verify transaction is displayed on screen in the transaction list.
        onView(withText("R 21.43")).check(matches(isDisplayed()))
        // Verify previous transaction is not displayed.
        onView(withText("R 12.34")).check(doesNotExist())

        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

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
