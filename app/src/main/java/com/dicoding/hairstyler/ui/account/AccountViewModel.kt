package com.dicoding.hairstyler.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
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