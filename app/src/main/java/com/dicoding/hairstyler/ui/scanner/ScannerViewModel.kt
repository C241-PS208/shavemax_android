package com.dicoding.hairstyler.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import java.io.File

class ScannerViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel() {
    fun predict(image : File, gender: String) = repositoryImpl.predict(image, gender)

    fun getUser() : LiveData<UserModel> {
        return repositoryImpl.getToken().asLiveData()
    }
}