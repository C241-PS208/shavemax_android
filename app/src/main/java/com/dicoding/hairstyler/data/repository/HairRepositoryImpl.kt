package com.dicoding.hairstyler.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.hairstyler.data.local.preference.SessionPreference
import com.dicoding.hairstyler.data.local.room.SavedHairstyle
import com.dicoding.hairstyler.data.local.room.SavedHairstyleDao
import com.dicoding.hairstyler.data.remote.response.ErrorResponse
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.remote.retrofit.ApiService
import com.dicoding.hairstyler.utils.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class HairRepositoryImpl (private val apiService: ApiService, private val savedHairstyleDao: SavedHairstyleDao) : HairRepository{
    private val diskIO: Executor = Executors.newSingleThreadExecutor()

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

    fun insert(hairstyle: SavedHairstyle) {
        diskIO.execute { savedHairstyleDao.insert(hairstyle) }
    }

    fun delete(hairstyle: SavedHairstyle) {
        diskIO.execute { savedHairstyleDao.delete(hairstyle) }
    }

    fun getSavedHairstyles(): LiveData<ResultState<List<SavedHairstyle>>> {
        val resultLiveData = MutableLiveData<ResultState<List<SavedHairstyle>>>()

        resultLiveData.value = ResultState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val hairstyleResponse = savedHairstyleDao.getSavedHairstyles()
                resultLiveData.postValue(ResultState.Success(hairstyleResponse))
            } catch (e: HttpException) {
                val errorMessage = extractErrorMessage(e)
                resultLiveData.postValue(ResultState.Error(errorMessage))
            } catch (e: SocketTimeoutException) {
                val errorMessage = "Request timed out. Please try again."
                resultLiveData.postValue(ResultState.Error(errorMessage))
            } catch (e: Exception) {
                val errorMessage = "An unexpected error occurred: ${e.localizedMessage}"
                resultLiveData.postValue(ResultState.Error(errorMessage))
            }
        }

        return resultLiveData
    }

    fun checkSaved(name : String): LiveData<SavedHairstyle> {
        return savedHairstyleDao.getSavedHairstyleByName(name)
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
        fun getRepositoryInstance(apiService: ApiService, savedHairstyleDao: SavedHairstyleDao) : HairRepositoryImpl{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: HairRepositoryImpl(apiService, savedHairstyleDao)
            }.also { INSTANCE = it }
        }
    }
}