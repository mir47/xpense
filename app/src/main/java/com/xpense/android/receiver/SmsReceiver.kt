package com.xpense.android.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import androidx.compose.material.ExperimentalMaterialApi
import com.xpense.android.R
import com.xpense.android.util.sendNotification

@ExperimentalMaterialApi
class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        println("mmmmm Intent received: " + intent.action)

        if (intent.action === SMS_RECEIVED) {
            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>?
                val messages: Array<SmsMessage?> = arrayOfNulls(pdus!!.size)
                for (i in pdus.indices) {
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                }
                if (messages.isNotEmpty()) {
                    println("mmmmm Message received: " + messages[0]?.messageBody)
                    println("mmmmm Message received from: " + messages[0]?.originatingAddress)

                    messages[0]?.messageBody?.takeIf {
                        it.startsWith("Standard Bank:")
                    }?.let { message ->
                        val smsData = message.split(" ")[2]
                        val notificationManager: NotificationManager =
                            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.sendNotification(
                            context,
                            context.getString(R.string.sms_notification_text),
                            smsData
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}
