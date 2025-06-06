package com.ankit.cinetail.ui.auth

import android.app.Activity
import android.util.Log
import androidx.credentials.CustomCredential
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankit.cinetail.data.auth.AuthManager
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    private val _authState = MutableLiveData<FirebaseUser?>()
    val authState: LiveData<FirebaseUser?> = _authState

    fun getCurrentUser() {
        _authState.value = authManager.getCurrentUser()
    }

    fun signIn(activity: Activity) {
        viewModelScope.launch {
            try {
                val credential = authManager.beginSignIn(activity)
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val token = GoogleIdTokenCredential.createFrom(credential.data).idToken
                    authManager.firebaseAuthWithGoogle(token) { success, user ->
                        _authState.value = if (success) user else null
                        Log.d("TAG", "AuthViewModel: $user")
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign-in failed: ${e.message}")
                _authState.value = null
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authManager.signOut()
            _authState.postValue(null)
        }
    }
}