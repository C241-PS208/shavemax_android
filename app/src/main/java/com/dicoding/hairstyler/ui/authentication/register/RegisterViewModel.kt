package com.dicoding.hairstyler.ui.authentication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.hairstyler.data.remote.response.ErrorResponse
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RegisterViewModel(private val repositoryImpl: UserRepositoryImpl) : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> = _status

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    fun registerUser(name: String, email: String, password: String, gender: String) {
        viewModelScope.launch {
            try {
                repositoryImpl.signUp(name, email, password, gender)
                _status.postValue(true)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                    _errorMessage.postValue(errorResponse.title) // Default Value is NEEDED to prevent error
                }
                _status.postValue(false)
                Log.e(TAG, e.toString(),e)
            } catch (e: SocketTimeoutException) {
                _errorMessage.postValue("The request timed out. Please check your connection and try again.")
                _status.postValue(false)
                Log.e(TAG, "Socket Timeout Exception: ${e.message ?: "Operation timed out"}",e)
            } catch (e: Exception) {
                _errorMessage.postValue("An unexpected error occurred. Please try again.")
                _status.postValue(false)
                Log.e(TAG, "Unknown Exception: ${e.message}",e)
            }
        }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}