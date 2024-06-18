package com.dicoding.hairstyler.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.remote.response.HairstyleResponse
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel (private val hairRepositoryImpl: HairRepositoryImpl, private val userRepositoryImpl: UserRepositoryImpl) : ViewModel() {
    private val _listHairstyle = MutableLiveData<List<HairstyleResponseItem>>()
    val listHairstyle : LiveData<List<HairstyleResponseItem>> = _listHairstyle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var fetched: Boolean = false
    fun getUser() : LiveData<UserModel>{
        return userRepositoryImpl.getToken().asLiveData()
    }

    fun getHairstyles() {
        if (!fetched) {
            _isLoading.value = true
            viewModelScope.launch {
                val result = hairRepositoryImpl.getHairstyles()
                result.onSuccess {
                    _isLoading.value = false
                    _listHairstyle.value = it
                    fetched = true
                }.onFailure {
                    // Handle the error
                    _isLoading.value = false
                    Log.e("HomeViewModel", "Failed to fetch hairstyles", it)
                }
            }
        }
    }
}