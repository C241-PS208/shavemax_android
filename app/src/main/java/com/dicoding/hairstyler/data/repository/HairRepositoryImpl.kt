package com.dicoding.hairstyler.data.repository

import com.dicoding.hairstyler.data.remote.response.ErrorResponse
import com.dicoding.hairstyler.data.remote.response.HairstyleResponse
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.remote.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

class HairRepositoryImpl (private val apiServiceOne: ApiService, private val apiServiceTwo: ApiService) : HairRepository {
    override suspend fun getHairstyles(): Result<List<HairstyleResponseItem>> {
        return try {
            val response = apiServiceOne.getAllHairstyle()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun extractErrorMessage(e: HttpException): String {
        return try {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            errorBody.message
        } catch (ex: Exception) {
            "Unknown Error"
        }
    }

    companion object {
        @Volatile

        private var INSTANCE : HairRepositoryImpl? = null
        fun getRepositoryInstance(apiServiceOne: ApiService, apiServiceTwo: ApiService) : HairRepositoryImpl{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: HairRepositoryImpl(apiServiceOne, apiServiceTwo)
            }.also { INSTANCE = it }
        }
    }

}