package com.dicoding.hairstyler.data.repository

import com.dicoding.hairstyler.data.local.preference.SessionPreference
import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import kotlinx.coroutines.flow.Flow

class RepositoryImpl (private val sessionPreference: SessionPreference) : Repository{
    override suspend fun saveToken(signInSuccessResponse: SignInSuccessResponse) {
        sessionPreference.saveToken(signInSuccessResponse)
    }

    override fun getToken(): Flow<SignInSuccessResponse> {
        return sessionPreference.getToken()
    }

    override suspend fun logOut() {
        sessionPreference.logOut()
    }

    companion object {
        @Volatile

        private var INSTANCE : RepositoryImpl? = null
        fun getRepositoryInstance(sessionPreference: SessionPreference) : RepositoryImpl{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: RepositoryImpl(sessionPreference)
            }.also { INSTANCE = it }
        }
    }

}