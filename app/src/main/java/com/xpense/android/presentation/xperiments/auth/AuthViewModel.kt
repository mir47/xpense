package com.xpense.android.presentation.xperiments.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import com.xpense.android.R

class AuthViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    /**
     * Get a message to display based on user set preference of which type they
     * want to see, or default if no user is logged in or no preference is set.
     */
    fun getTextToDisplay(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val welcomeTypePreferenceKey = context.getString(R.string.preference_welcome_type_key)
        val defaultWelcomeType = context.resources.getStringArray(R.array.welcome_type)[0]
        val welcomeType = sharedPreferences.getString(welcomeTypePreferenceKey, defaultWelcomeType)
        return welcomeType ?: defaultWelcomeType
    }
}
