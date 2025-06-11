
package com.ankit.cinetail.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ankit.cinetail.data.auth.AuthManager
import com.ankit.cinetail.data.local.PreferenceHelper
import com.ankit.cinetail.data.remote.ApiConstants
import com.ankit.cinetail.data.remote.MovieApiService
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

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("cinetail_preferences")
        }
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(
        dataStore: DataStore<Preferences>
    ): PreferenceHelper {
        return PreferenceHelper(dataStore)
    }

}