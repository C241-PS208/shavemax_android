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

class SessionPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveToken(
        userModel: UserModel
    ) {
        dataStore.edit {mutablePreferences ->
            mutablePreferences[TOKEN_KEY] = userModel.token
            mutablePreferences[NAME_KEY] = userModel.name
            mutablePreferences[EMAIL_KEY] = userModel.email
            mutablePreferences[GENDER_KEY] = userModel.gender

        }
    }

    fun getToken() : Flow<UserModel> {
        return dataStore.data.map {value: Preferences ->
            UserModel(
                token = value[TOKEN_KEY] ?: "",
                name = value[NAME_KEY] ?: "",
                email = value[EMAIL_KEY] ?: "",
                gender = value[GENDER_KEY] ?: "",
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
        private val GENDER_KEY = stringPreferencesKey("userId")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")

        private var INSTANCE : SessionPreference? = null
        fun getPreferenceInstance(dataStore: DataStore<Preferences>) : SessionPreference{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: SessionPreference(dataStore)
            }.also { INSTANCE = it }
        }
    }

}