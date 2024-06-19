package com.dicoding.shavemax.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.shavemax.data.local.preference.UserModel
import com.dicoding.shavemax.data.remote.response.HairstyleResponseItem
import com.dicoding.shavemax.data.remote.response.NewsResponse
import com.dicoding.shavemax.data.remote.response.ResultResponse
import com.dicoding.shavemax.data.remote.response.SignUpSuccessResponse
import com.dicoding.shavemax.utils.ResultState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {

    suspend fun saveToken(userModel: UserModel)
    fun getToken() : Flow<UserModel>

    suspend fun signUp(name: String, email: String, password: String, gender: String): SignUpSuccessResponse

    suspend fun signIn(email: String, password: String): UserModel
    suspend fun logOut()

    fun predict(image : File, gender: String) : LiveData<ResultState<ResultResponse>>

    fun getAllHairstyle() : LiveData<ResultState<List<HairstyleResponseItem>>>

    fun getHairstyleNews() : LiveData<ResultState<NewsResponse>>
}