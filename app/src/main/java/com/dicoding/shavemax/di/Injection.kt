package com.dicoding.shavemax.di

import android.content.Context
import com.dicoding.shavemax.data.local.preference.SessionPreference
import com.dicoding.shavemax.data.local.preference.dataStore
import com.dicoding.shavemax.data.local.room.SavedHairstyleDatabase
import com.dicoding.shavemax.data.remote.retrofit.ApiConfig
import com.dicoding.shavemax.data.repository.HairRepositoryImpl
import com.dicoding.shavemax.data.repository.UserRepositoryImpl

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
        val database = SavedHairstyleDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiServiceOne(sessionPreference)
        return HairRepositoryImpl.getRepositoryInstance(apiService, database.dao())
    }
}