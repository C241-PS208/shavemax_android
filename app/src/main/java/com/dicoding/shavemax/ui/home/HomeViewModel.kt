package com.dicoding.shavemax.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.shavemax.data.local.preference.UserModel
import com.dicoding.shavemax.data.remote.response.HairstyleResponseItem
import com.dicoding.shavemax.data.repository.HairRepositoryImpl
import com.dicoding.shavemax.data.repository.UserRepositoryImpl
import com.dicoding.shavemax.utils.ResultState

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