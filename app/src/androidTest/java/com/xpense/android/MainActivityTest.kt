package com.xpense.android

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.xpense.android.data.Transaction
import com.xpense.android.data.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var repository: TransactionRepository

    @Before
    fun init() {
        // Not using fake repository
        repository = ServiceLocator.provideTransactionRepository(getApplicationContext())

        // use runBlocking because this itself is not a test, so we don't
        // need access to TestCoroutineDispatcher
        runBlocking {
            // clear any previous state from other tests
            repository.deleteAllTransactions()
        }
    }

    @After
    fun reset() = ServiceLocator.resetRepository()

    @Test
    fun templateTest() = runBlocking {
        // Set initial state.
        repository.saveTransaction(Transaction(transactionId = 1))

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
        repository.saveTransaction(Transaction(
            transactionId = 1,
            amount = 12.34,
            description = "description")
        )

        // Start up Transactions screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

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
}
