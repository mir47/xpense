package com.xpense.android.presentation.ui.experiments.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.xpense.android.R
import com.xpense.android.databinding.FragmentAuthBinding
import timber.log.Timber
import androidx.navigation.fragment.findNavController

class AuthFragment : Fragment() {

    private val _authViewModel by viewModels<AuthViewModel>()

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater)
        binding.settingsButton.setOnClickListener {
            val action = AuthFragmentDirections.actionLoginFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    // TODO: onActivityResult is deprecated
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FIREBASE_AUTH_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Timber.i("Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Timber.i("Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()

            // This is where you can provide more ways for users to register and
            // sign in.
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the SIGN_IN_REQUEST_CODE
        // TODO: startActivityForResult is deprecated
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            FIREBASE_AUTH_REQUEST_CODE
        )
    }

    private fun signOut() = AuthUI.getInstance().signOut(requireContext())

    /**
     * Observes the authentication state and changes the UI accordingly.
     * If there is a logged in user: (1) show a logout button and (2) display their name.
     * If there is no logged in user: show a login button
     */
    private fun observeAuthenticationState() {
        val welcomeText = _authViewModel.getTextToDisplay(requireContext())
        _authViewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                AuthViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.authText.text = getTextWithPersonalization(welcomeText)
                    binding.authButton.text = getString(R.string.logout_button_text)
                    binding.authButton.setOnClickListener { signOut() }
                }
                else -> {
                    binding.authText.text = getString(R.string.welcome_message)
                    binding.authButton.text = getString(R.string.login_button_text)
                    binding.authButton.setOnClickListener { launchSignInFlow() }
                }
            }
        }
    }

    private fun getTextWithPersonalization(text: String): String {
        return String.format(
            getString(
                R.string.welcome_message_authed,
                text,
                FirebaseAuth.getInstance().currentUser?.displayName
            )
        )
    }
}
