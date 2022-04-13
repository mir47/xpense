package com.xpense.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseTest {

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Creates a single TestCoroutineDispatcher
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Convenience methods for mainCoroutineRule
    fun coroutineTest(block: suspend TestCoroutineScope.() -> Unit) =
        mainCoroutineRule.runBlockingTest { block() }
    fun pauseCoroutine() = mainCoroutineRule.pauseDispatcher()
    fun resumeCoroutine() = mainCoroutineRule.resumeDispatcher()
}
