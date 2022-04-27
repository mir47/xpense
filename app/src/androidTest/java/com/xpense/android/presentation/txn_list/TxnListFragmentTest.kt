package com.xpense.android.presentation.txn_list

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xpense.android.R
import com.xpense.android.di.ServiceLocator
import com.xpense.android.domain.repository.FakeAndroidTxnRepository
import com.xpense.android.data.source.local.model.TxnEntity
import com.xpense.android.domain.repository.TxnRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TxnListFragmentTest {

    private lateinit var repository: TxnRepository

    @Before
    fun setup() {
        repository = FakeAndroidTxnRepository()
        ServiceLocator.repository = repository
    }

    @After
    fun cleanUp() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() = runBlockingTest {
        // GIVEN - Add active (incomplete) task to the DB
        repository.saveTransaction(TxnEntity(1, amount = 1.2, description = "one"))
        repository.saveTransaction(TxnEntity(2, amount = 2.22, description = "two"))

        // WHEN - Details fragment launched to display task
        launchFragmentInContainer<TxnListFragment>(Bundle(), R.style.Theme_Xpense)

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

    @Test
    fun clickItem_navigateTo() = runBlockingTest {
        // GIVEN - On the transactions screen with two items
        repository.saveTransaction(TxnEntity(1, amount = 1.2, description = "one"))
        repository.saveTransaction(TxnEntity(2, amount = 2.22, description = "two"))

        val scenario = launchFragmentInContainer<TxnListFragment>(Bundle(), R.style.Theme_Xpense)

        // Use Mockito to create NavController mock
        val navController = mock(NavController::class.java)

        // Attach mock NavController to fragment
        scenario.onFragment { Navigation.setViewNavController(it.view!!, navController) }

        // WHEN - Click on the first list item
        onView(withId(R.id.transaction_list))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("one")), click()))

        // THEN - Verify navigation with correct param(s)
        verify(navController).navigate(
            TxnListFragmentDirections
                .actionTxnListFragmentToTxnAddEditFragment()
                .setTransactionId(1)
        )

        // WHEN - Click on the second list item
        onView(withId(R.id.transaction_list))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("two")), click()))

        // THEN - Verify navigation with correct param(s)
        verify(navController).navigate(
            TxnListFragmentDirections
                .actionTxnListFragmentToTxnAddEditFragment()
                .setTransactionId(2)
        )
    }
}
