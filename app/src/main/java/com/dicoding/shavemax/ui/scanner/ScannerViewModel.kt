package com.dicoding.shavemax.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.shavemax.data.local.preference.UserModel
import com.dicoding.shavemax.data.repository.UserRepositoryImpl
import java.io.File

class ScannerViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel() {
    fun predict(image : File, gender: String) = repositoryImpl.predict(image, gender)

    fun getUser() : LiveData<UserModel> {
        return repositoryImpl.getToken().asLiveData()
    }
}