package com.sandwich.kabaya.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.sandwich.kabaya.telegram.Messenger

class KabayaBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != (Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            return
        }

        // Slot 0: bottom slot.
        // Slot 1: top slot. It is combined with memory card on my S8
        val slot = intent.extras?.getInt("android.telephony.extra.SLOT_INDEX", -1) ?: -1

        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        val messagesByOriginatingAddress = smsMessages.groupBy { it.originatingAddress }
        for ((address, messages) in messagesByOriginatingAddress) {
            val strMessage = StringBuilder()
            for (message in messages) {
                strMessage.append(message.messageBody)
            }

            val notificationText = "SMS from $address: $strMessage"
            Messenger.sendTelegramMessageForSlot(slot, notificationText, context)
        }
    }
}
