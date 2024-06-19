package com.dicoding.hairstyler.ui.news

import androidx.lifecycle.ViewModel
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl

class NewsViewModel (private val repositoryImpl: UserRepositoryImpl): ViewModel() {

    fun getHairstyleNews() = repositoryImpl.getHairstyleNews()
}