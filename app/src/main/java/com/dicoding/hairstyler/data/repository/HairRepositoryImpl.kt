package com.dicoding.hairstyler.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.hairstyler.data.local.preference.SessionPreference
import com.dicoding.hairstyler.data.remote.response.ErrorResponse
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.remote.retrofit.ApiService
import com.dicoding.hairstyler.utils.ResultState
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.SocketTimeoutException

class HairRepositoryImpl (private val apiService: ApiService) : HairRepository{
    override fun getAllHairstyle(): LiveData<ResultState<List<HairstyleResponseItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val hairstyleResponse = apiService.getAllHairstyle()
            emit(ResultState.Success(hairstyleResponse))
        } catch (e: HttpException) {
            val errorMessage = extractErrorMessage(e)
            emit(ResultState.Error(errorMessage))
        } catch (e: SocketTimeoutException) {
            val errorMessage = "Request timed out. Please try again."
            emit(ResultState.Error(errorMessage))
        } catch (e: Exception) {
            val errorMessage = "An unexpected error occurred: ${e.localizedMessage}"
            emit(ResultState.Error(errorMessage))
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
        fun getRepositoryInstance(apiService: ApiService) : HairRepositoryImpl{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: HairRepositoryImpl(apiService)
            }.also { INSTANCE = it }
        }
    }
}