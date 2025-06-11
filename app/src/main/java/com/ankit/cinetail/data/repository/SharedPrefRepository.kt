package com.ankit.cinetail.data.repository

import com.ankit.cinetail.data.local.PreferenceHelper
import javax.inject.Inject

class SharedPrefRepository  @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) {
    val isLoggedIn = preferenceHelper.isLoggedInFlow
    val userName = preferenceHelper.userNameFlow.toString()

    suspend fun setLoginStatus(status: Boolean) {
        preferenceHelper.setLoggedIn(status)
    }

    suspend fun setUserName(name: String) {
        preferenceHelper.setUserName(name)
    }
}