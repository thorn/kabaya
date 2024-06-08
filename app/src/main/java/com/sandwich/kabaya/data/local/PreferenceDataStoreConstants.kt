package com.sandwich.kabaya.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreConstants {
    val TELEGRAM_BOT_TOKEN = stringPreferencesKey("TELEGRAM_BOT_TOKEN")
    val TELEGRAM_SLOT1_ID = stringPreferencesKey("TELEGRAM_SLOT1_ID")
    val TELEGRAM_SLOT2_ID = stringPreferencesKey("TELEGRAM_SLOT2_ID")
    val TELEGRAM_SLOT1_NAME = stringPreferencesKey("TELEGRAM_SLOT1_NAME")
    val TELEGRAM_SLOT2_NAME = stringPreferencesKey("TELEGRAM_SLOT2_NAME")
}