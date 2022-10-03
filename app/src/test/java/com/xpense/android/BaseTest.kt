package com.xpense.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseTest {

    // Executes each task synchronously using Architecture Components
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Sets up the test dispatcher
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    // Convenience methods for testCoroutineRule
    fun runTest(block: suspend () -> Unit) = testCoroutineRule.runTest { block() }

}
