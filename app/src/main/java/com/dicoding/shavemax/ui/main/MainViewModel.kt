package com.dicoding.shavemax.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.shavemax.data.local.preference.UserModel
import com.dicoding.shavemax.data.repository.UserRepositoryImpl

class MainViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel(){

    fun getToken() : LiveData<UserModel>{
        return repositoryImpl.getToken().asLiveData()
    }
}