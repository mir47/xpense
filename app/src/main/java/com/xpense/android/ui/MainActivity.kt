package com.xpense.android.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import com.xpense.android.R
import com.xpense.android.util.SMS_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(
            getString(R.string.sms_notification_channel_id),
            getString(R.string.sms_notification_channel_name),
            getString(R.string.sms_notification_channel_description)
        )
        createNotificationChannel(
            getString(R.string.fcm_notification_channel_id),
            getString(R.string.fcm_notification_channel_name),
            getString(R.string.fcm_notification_channel_description)
        )

        // check if launched from notification from SMS detection
        intent?.extras?.getString(SMS_EXTRA)?.let {
//            navController.navigate(
//                LegacyTxnListFragmentDirections
//                    .actionLegacyTxnListFragmentToTxnAddEditFragment()
//                    .setSms(it)
//            )
        }

        setContent {
            XpenseApp()
        }
    }

    private fun createNotificationChannel(
        channelId: String,
        channelName: String,
        channelDescription: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setShowBadge(false)
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = channelDescription
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
