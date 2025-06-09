package com.ankit.cinetail.data.repository

import com.ankit.cinetail.data.local.PreferenceHelper
import javax.inject.Inject

class SharedPrefRepository  @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) {
    val isLoggedIn = preferenceHelper.isLoggedInFlow

    suspend fun setLoginStatus(status: Boolean) {
        preferenceHelper.setLoggedIn(status)
    }
}