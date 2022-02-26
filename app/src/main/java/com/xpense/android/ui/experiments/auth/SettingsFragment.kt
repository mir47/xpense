package com.xpense.android.ui.experiments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.xpense.android.R
import timber.log.Timber

class SettingsFragment : PreferenceFragmentCompat() {

    // Get a reference to the ViewModel scoped to this Fragment
    private val _authViewModel by viewModels<AuthViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // TODO: set prefs separately per user, because current prefs is shared
        setPreferencesFromResource(R.xml.login_settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        _authViewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                AuthViewModel.AuthenticationState.AUTHENTICATED -> Timber.i("Authenticated")
                // If the user is not logged in, they should not be able to set any preferences,
                // so launch the sign in flow
                AuthViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(R.id.login_fragment)
                else -> Timber.e("New $authenticationState state that doesn't require any UI change")
            }
        }
    }
}
