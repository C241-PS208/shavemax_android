package com.dicoding.hairstyler.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import com.dicoding.hairstyler.data.repository.RepositoryImpl

class MainViewModel (private val repositoryImpl: RepositoryImpl) : ViewModel(){

    fun getToken() : LiveData<SignInSuccessResponse>{
        return repositoryImpl.getToken().asLiveData()
    }
}