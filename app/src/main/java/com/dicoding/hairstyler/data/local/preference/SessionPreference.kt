package com.dicoding.hairstyler.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionPreference private constructor(val dataStore: DataStore<Preferences>) {

    suspend fun saveToken(
        signInSuccessResponse: SignInSuccessResponse
    ) {
        dataStore.edit {mutablePreferences ->
            mutablePreferences[TOKEN_KEY] = signInSuccessResponse.token
        }
    }

    fun getToken() : Flow<SignInSuccessResponse> {
        return dataStore.data.map {value: Preferences ->
            SignInSuccessResponse(
                token = value[TOKEN_KEY] ?: "",
            )
        }
    }

    suspend fun logOut() {
        dataStore.edit {mutablePreferences ->
            mutablePreferences.clear()
        }
    }

    companion object{
        @Volatile

        private var TOKEN_KEY = stringPreferencesKey("token")

        private var INSTANCE : SessionPreference? = null
        fun getPreferenceInstance(dataStore: DataStore<Preferences>) : SessionPreference{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: SessionPreference(dataStore)
            }.also { INSTANCE = it }
        }
    }

}