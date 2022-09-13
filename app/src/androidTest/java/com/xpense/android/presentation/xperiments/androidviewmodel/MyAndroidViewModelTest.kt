package com.xpense.android.presentation.xperiments.androidviewmodel

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class MyAndroidViewModelTest {

    // Subject under test
    private lateinit var vm: MyAndroidViewModel

    @Before
    fun setup() {
        // Initialise view model with mock application
        val mockApp = ApplicationProvider.getApplicationContext() as Application
        vm = MyAndroidViewModel(mockApp)
    }

    @Test
    fun textState_returnsResolvedString() {
        // When view model is created with text state
        val text = vm.text.value

        // Then text returns resolved string resource
        assertEquals("Hello Android View Model", text)
    }
}
