package com.dicoding.hairstyler.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.hairstyler.data.local.preference.UserModel
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import com.dicoding.hairstyler.utils.ResultState

class HomeViewModel (private val repositoryImpl: UserRepositoryImpl, private val hairRepositoryImpl: HairRepositoryImpl) : ViewModel() {
    lateinit var hairList : LiveData<ResultState<List<HairstyleResponseItem>>>

    init {
        getAllHairstyle()
    }

    fun getUser() : LiveData<UserModel>{
        return repositoryImpl.getToken().asLiveData()
    }

    private fun getAllHairstyle() {
        hairList = hairRepositoryImpl.getAllHairstyle()
    }
}