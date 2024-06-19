package com.dicoding.shavemax.ui.news

import androidx.lifecycle.ViewModel
import com.dicoding.shavemax.data.repository.UserRepositoryImpl

class NewsViewModel (private val repositoryImpl: UserRepositoryImpl): ViewModel() {

    fun getHairstyleNews() = repositoryImpl.getHairstyleNews()
}