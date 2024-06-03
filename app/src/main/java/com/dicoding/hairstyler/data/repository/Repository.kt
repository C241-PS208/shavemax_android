package com.dicoding.hairstyler.data.repository

import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun saveToken(signInSuccessResponse: SignInSuccessResponse)
    fun getToken() : Flow<SignInSuccessResponse>
    suspend fun logOut()
}