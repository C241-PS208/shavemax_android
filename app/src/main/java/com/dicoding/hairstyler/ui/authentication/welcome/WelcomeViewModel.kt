package com.dicoding.hairstyler.ui.authentication.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl

class WelcomeViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel() {

    fun getToken() : LiveData<UserModel> {
        return repositoryImpl.getToken().asLiveData()
    }
}