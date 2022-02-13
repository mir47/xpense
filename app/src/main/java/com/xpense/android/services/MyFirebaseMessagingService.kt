package com.xpense.android.services

import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("onNewToken: $token")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.w("Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val tokenResult = task.result

            // Log and toast
            val msg = "new token: $tokenResult"
            Timber.d(msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

            // If you want to send messages to this application instance or
            // manage this apps subscriptions on the server side, send the
            // FCM registration token to your app server.
            //sendRegistrationToServer(token)
        })
    }
}
