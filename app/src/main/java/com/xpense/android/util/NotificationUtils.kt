package com.xpense.android.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.xpense.android.MainActivity
import com.xpense.android.R

// Notification ID
private val NOTIFICATION_ID = 0
const val SMS_EXTRA = "sms_extra"

/**
 * Builds and delivers the notification.
 *
 * @param context Context
 * @param messageBody, notification text.
 */
fun NotificationManager.sendNotification(
    context: Context,
    messageBody: String,
    smsData: String? = null
) {
    // Create an explicit intent for the activity to be launched
    val contentIntent = Intent(context, MainActivity::class.java)
        .apply { putExtra(SMS_EXTRA, smsData) }

    val contentPendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val icon = BitmapFactory.decodeResource(context.resources, android.R.drawable.ic_dialog_info)

    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(icon)
            .bigLargeIcon(null)

    val channelId = context.getString(R.string.sms_notification_channel_id)
    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(context.getString(R.string.sms_notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(bigPicStyle)
        .setLargeIcon(icon)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    notify(NOTIFICATION_ID, notification)
}
