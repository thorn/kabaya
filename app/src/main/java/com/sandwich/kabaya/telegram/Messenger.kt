package com.sandwich.kabaya.telegram

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandwich.kabaya.data.local.PreferenceDataStoreConstants
import com.sandwich.kabaya.data.local.PreferenceDataStoreHelper
import com.sandwich.kabaya.sms_handlers.GeorgianLanguageHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Messenger {
    companion object {
        private fun sendTelegramMessage(botToken: String, messageString: String, chatId: String, context: Context) {
            val tgEndpoint = "https://api.telegram.org/bot${botToken}/sendMessage"

            val queue = Volley.newRequestQueue(context)
            // Request a string response from the provided URL.
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                tgEndpoint,
                Response.Listener { response -> print(response) },
                Response.ErrorListener {
                    // print error message
                    print(it.message)
                    print("error")
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    var markupKeyboard = GeorgianLanguageHandler().handle(messageString) ?: ""
                    params["chat_id"] = chatId
                    params["reply_markup"] = markupKeyboard
                    params["text"] = messageString
                    return params
                }
            }
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }

        fun sendTelegramMessageForSlot(slot: Int, messageString: String, context: Context) {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                val preferenceDataStoreHelper = PreferenceDataStoreHelper(context)
                val token: String = preferenceDataStoreHelper.getFirstPreference(
                    PreferenceDataStoreConstants.TELEGRAM_BOT_TOKEN,
                    ""
                )

                val receiver = if (slot == 1) PreferenceDataStoreConstants.TELEGRAM_SLOT2_ID else PreferenceDataStoreConstants.TELEGRAM_SLOT1_ID

                val chatId: String = preferenceDataStoreHelper.getFirstPreference(
                    receiver,
                    ""
                )
                Messenger.sendTelegramMessage(token, messageString, chatId, context)
            }
        }
    }
}