package com.dicoding.hairstyler.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl

class MainViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel(){

    fun getToken() : LiveData<UserModel>{
        return repositoryImpl.getToken().asLiveData()
    }
}