package com.dicoding.hairstyler.di

import android.content.Context
import com.dicoding.hairstyler.data.local.preference.SessionPreference
import com.dicoding.hairstyler.data.local.preference.dataStore
import com.dicoding.hairstyler.data.remote.retrofit.ApiConfig
import com.dicoding.hairstyler.data.repository.HairRepository
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl

object Injection {
    fun provideUserRepository(context: Context) : UserRepositoryImpl{
        val sessionPreference = SessionPreference.getPreferenceInstance(context.dataStore)
        val apiServiceOne = ApiConfig.getApiServiceOne(sessionPreference)
        val apiServiceTwo = ApiConfig.getApiServiceTwo(sessionPreference)
        val newsApiService = ApiConfig.getNewsApiService()
        return UserRepositoryImpl.getRepositoryInstance(sessionPreference, apiServiceOne, apiServiceTwo, newsApiService)
    }

    fun provideHairRepository(context: Context) : HairRepositoryImpl{
        val sessionPreference = SessionPreference.getPreferenceInstance(context.dataStore)
        val apiService = ApiConfig.getApiServiceOne(sessionPreference)
        return HairRepositoryImpl.getRepositoryInstance(apiService)
    }
}