package com.dicoding.shavemax.ui.authentication.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.shavemax.data.local.preference.UserModel
import com.dicoding.shavemax.data.repository.UserRepositoryImpl

class WelcomeViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel() {

    fun getToken() : LiveData<UserModel> {
        return repositoryImpl.getToken().asLiveData()
    }
}