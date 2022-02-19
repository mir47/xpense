package com.xpense.android.ui.transactions

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xpense.android.R
import com.xpense.android.ServiceLocator
import com.xpense.android.data.FakeAndroidTransactionRepository
import com.xpense.android.data.Transaction
import com.xpense.android.data.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TransactionsFragmentTest {

    private lateinit var repository: TransactionRepository

    @Before
    fun setup() {
        repository = FakeAndroidTransactionRepository()
        ServiceLocator.repository = repository
    }

    @After
    fun cleanUp() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() = runBlockingTest {
        // GIVEN - Add active (incomplete) task to the DB
        val t1 = Transaction(1, amount = 1.2, description = "one")
        repository.insertTransaction(t1)
        val t2 = Transaction(2, amount = 2.22, description = "two")
        repository.insertTransaction(t2)

        // WHEN - Details fragment launched to display task
        launchFragmentInContainer<TransactionsFragment>(Bundle(), R.style.Theme_Xpense)

//        Thread.sleep(2000)

        // THEN - Task details are displayed on the screen
        // make sure that the list is shown
        onView(withId(R.id.transaction_list))
            .check(matches(isDisplayed()))

//        // and make sure the item is shown with correct values
//        onView(withId(R.id.star_image))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.timestamp))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.amount))
//            .check(matches(isDisplayed()))
//            .check(matches(withText("R 1.20")))
//        onView(withId(R.id.description))
//            .check(matches(isDisplayed()))
//            .check(matches(withText("one")))
//
//        // and make sure the item is shown with correct values
//        onView(withId(R.id.star_image))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.timestamp))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.amount))
//            .check(matches(isDisplayed()))
//            .check(matches(withText("R 2.22")))
//        onView(withId(R.id.description))
//            .check(matches(isDisplayed()))
//            .check(matches(withText("two")))

        onView(withText("R 1.20"))
            .check(matches(isDisplayed()))
        onView(withText("one"))
            .check(matches(isDisplayed()))

        onView(withText("R 2.22"))
            .check(matches(isDisplayed()))
        onView(withText("two"))
            .check(matches(isDisplayed()))
    }
}
