package com.dicoding.shavemax.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.shavemax.data.local.preference.UserModel
import com.dicoding.shavemax.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class AccountViewModel(private val repositoryImpl: UserRepositoryImpl) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            repositoryImpl.logOut()
        }
    }

    fun getUser() : LiveData<UserModel>{
        return repositoryImpl.getToken().asLiveData()
    }
}