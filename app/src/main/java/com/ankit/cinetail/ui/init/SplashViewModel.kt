package com.ankit.cinetail.ui.init

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankit.cinetail.data.auth.AuthManager
import com.ankit.cinetail.data.repository.SharedPrefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val sharedPrefRepository: SharedPrefRepository
) : ViewModel() {

    private val _authStatus = MutableLiveData<Boolean>()
    val authStatus: LiveData<Boolean> = _authStatus

    suspend fun checkAuthenticationStatus() {
        viewModelScope.launch {
            try {
                // Check if user is logged in from DataStore
                val isLoggedInFromPrefs = sharedPrefRepository.isLoggedIn.first()
                
                // Check if Firebase user exists
                val currentFirebaseUser = authManager.getCurrentUser()
                
                // User is authenticated if both conditions are true
                val isAuthenticated = isLoggedInFromPrefs && currentFirebaseUser != null
                
                // If DataStore says logged in but Firebase user doesn't exist, clear the preference
                if (isLoggedInFromPrefs && currentFirebaseUser == null) {
                    sharedPrefRepository.setLoginStatus(false)
                }
                
                // If Firebase user exists but DataStore says not logged in, update preference
                if (!isLoggedInFromPrefs && currentFirebaseUser != null) {
                    sharedPrefRepository.setLoginStatus(true)
                }
                
                _authStatus.postValue(isAuthenticated)
                
            } catch (e: Exception) {
                // In case of any error, assume user is not authenticated
                _authStatus.postValue(false)
            }
        }
    }
}