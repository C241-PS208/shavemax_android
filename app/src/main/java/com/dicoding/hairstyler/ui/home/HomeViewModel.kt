package com.dicoding.hairstyler.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl

class HomeViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel() {

    fun getUser() : LiveData<UserModel>{
        return repositoryImpl.getToken().asLiveData()
    }
}