package com.xpense.android.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import kotlinx.coroutines.runBlocking

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
                if (messages.size > -1) {
                    println("mmmmm Message received: " + messages[0]?.messageBody)
                    println("mmmmm Message received from: " + messages[0]?.originatingAddress)
                    runBlocking {
                        println("mmmmm ok")
                    }
                }
            }
        }
    }

    companion object {
        const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}