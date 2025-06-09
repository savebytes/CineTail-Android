package com.ankit.cinetail.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val USER_NAME = stringPreferencesKey("user_name") // example
    // Add more keys as needed
}
