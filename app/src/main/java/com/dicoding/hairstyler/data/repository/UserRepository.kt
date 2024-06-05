package com.dicoding.hairstyler.data.repository

import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import com.dicoding.hairstyler.data.remote.response.SignUpSuccessResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveToken(userModel: UserModel)
    fun getToken() : Flow<UserModel>

    suspend fun signUp(name: String, email: String, password: String, gender: String): SignUpSuccessResponse

    suspend fun signIn(email: String, password: String): UserModel
    suspend fun logOut()
}