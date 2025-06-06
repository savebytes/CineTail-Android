
package com.ankit.cinetail.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.ankit.cinetail.data.auth.AuthManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CTModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun provideAuthManager(
        firebaseAuth: FirebaseAuth,
        credentialManager: CredentialManager
    ): AuthManager {
        return AuthManager(firebaseAuth, credentialManager)
    }

}