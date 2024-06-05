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
                    _errorMessage.postValue(errorResponse.title ?: "Unknown Error") // Default Value is NEEDED to prevent error
                }
                _status.postValue(false)
                Log.e(TAG, e.toString())
            }
        }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}