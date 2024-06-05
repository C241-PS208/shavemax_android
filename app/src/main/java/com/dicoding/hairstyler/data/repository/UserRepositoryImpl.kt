package com.dicoding.hairstyler.data.repository

import android.util.Log
import com.dicoding.hairstyler.data.local.preference.SessionPreference
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.remote.request.SignInRequest
import com.dicoding.hairstyler.data.remote.request.SignUpRequest
import com.dicoding.hairstyler.data.remote.response.ErrorResponse
import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import com.dicoding.hairstyler.data.remote.response.SignUpSuccessResponse
import com.dicoding.hairstyler.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepositoryImpl (private val sessionPreference: SessionPreference, private val apiService: ApiService) : UserRepository{
    override suspend fun saveToken(userModel: UserModel) {
        sessionPreference.saveToken(userModel)
    }

    override fun getToken(): Flow<UserModel> {
        return sessionPreference.getToken()
    }

    override suspend fun signUp(name: String, email: String, password: String, gender: String): SignUpSuccessResponse {
        try {
            return apiService.signUp(SignUpRequest(name, email, password, gender))
        } catch (e: HttpException) {
            throw e
        }
    }

    override suspend fun signIn(email: String, password: String): UserModel {
        try {
            val user = apiService.signIn(SignInRequest(email, password))
            return UserModel(user.gender, user.name, user.email, user.token)
        } catch (e: HttpException) {
            throw e
        }
    }

    override suspend fun logOut() {
        sessionPreference.logOut()
    }

    companion object {
        @Volatile

        private var INSTANCE : UserRepositoryImpl? = null
        fun getRepositoryInstance(sessionPreference: SessionPreference, apiService: ApiService) : UserRepositoryImpl{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: UserRepositoryImpl(sessionPreference, apiService)
            }.also { INSTANCE = it }
        }
    }

}