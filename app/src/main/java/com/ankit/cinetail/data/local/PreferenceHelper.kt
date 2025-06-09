package com.ankit.cinetail.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ankit.cinetail.utils.PreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceHelper @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val isLoggedInFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[PreferenceKeys.IS_LOGGED_IN] ?: false }

    suspend fun setLoggedIn(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_LOGGED_IN] = value
        }
    }

    val userNameFlow: Flow<String?> = dataStore.data
        .map { preferences -> preferences[PreferenceKeys.USER_NAME] }

    suspend fun setUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_NAME] = name
        }
    }
}