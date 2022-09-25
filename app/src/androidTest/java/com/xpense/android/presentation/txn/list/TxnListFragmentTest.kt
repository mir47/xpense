package com.xpense.android.presentation.txn.list

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xpense.android.R
import com.xpense.android.di.ServiceLocator
import com.xpense.android.domain.model.Txn
import com.xpense.android.domain.repository.FakeAndroidTxnRepository
import com.xpense.android.domain.repository.TxnRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

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
    fun clickItem_navigateTo() = runBlockingTest {
        // GIVEN - On the transactions screen with two items
        repository.saveTransaction(Txn(1, amount = 1.2, description = "one"))
        repository.saveTransaction(Txn(2, amount = 2.22, description = "two"))

//        val scenario =
//            launchFragmentInContainer<LegacyTxnListFragment>(Bundle(), R.style.Theme_Xpense)
//
//        // Use Mockito to create NavController mock
//        val navController = Mockito.mock(NavController::class.java)
//
//        // Attach mock NavController to fragment
//        scenario.onFragment { Navigation.setViewNavController(it.view!!, navController) }
//
//        // WHEN - Click on the first list item
//        Espresso.onView(withId(R.id.transaction_list))
//            .perform(
//                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//                    ViewMatchers.hasDescendant(ViewMatchers.withText("one")), ViewActions.click()
//                )
//            )
//
//        // THEN - Verify navigation with correct param(s)
//        Mockito.verify(navController).navigate(
//            LegacyTxnListFragmentDirections.actionLegacyTxnListFragmentToTxnAddEditFragment()
//                .setTransactionId(1)
//        )
//
//        // WHEN - Click on the second list item
//        Espresso.onView(withId(R.id.transaction_list))
//            .perform(
//                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//                    ViewMatchers.hasDescendant(ViewMatchers.withText("two")), ViewActions.click()
//                )
//            )
//
//        // THEN - Verify navigation with correct param(s)
//        Mockito.verify(navController).navigate(
//            LegacyTxnListFragmentDirections.actionLegacyTxnListFragmentToTxnAddEditFragment()
//                .setTransactionId(2)
//        )
    }
}