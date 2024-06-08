package com.sandwich.kabaya.calls

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.telecom.Call
import android.telecom.CallScreeningService
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat

class KabayaCallService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {
        // TODO: find out how to get the slot index of the incoming call
         val incomingNumber = callDetails.handle.schemeSpecificPart
         val simSlotIndex = getSimSlotIndex(incomingNumber)
    }

    @SuppressLint("MissingPermission")
    private fun getSimSlotIndex(incomingNumber: String): Int {
        val subscriptionManager = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
        val activeSubscriptionInfoList = subscriptionManager.activeSubscriptionInfoList
        for (subscriptionInfo in activeSubscriptionInfoList) {
            val subscriptionId = subscriptionInfo.subscriptionId
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling ActivityCompat#requestPermissions for runtime permissions
                return -1
            }
            val simPhoneNumber = telephonyManager.getLine1Number()
            if (simPhoneNumber == incomingNumber) {
                return subscriptionInfo.simSlotIndex
            }
        }
        return -1
    }

    private fun sendToTelegram(context: Context, message: String) {
        // Implement Telegram API to send message
    }
}