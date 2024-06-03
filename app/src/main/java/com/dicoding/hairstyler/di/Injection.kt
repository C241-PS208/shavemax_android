package com.dicoding.hairstyler.di

import android.content.Context
import com.dicoding.hairstyler.data.local.preference.SessionPreference
import com.dicoding.hairstyler.data.local.preference.dataStore
import com.dicoding.hairstyler.data.repository.RepositoryImpl

object Injection {
    fun provideRepository(context: Context) : RepositoryImpl{
        val sessionPreference = SessionPreference.getPreferenceInstance(context.dataStore)
        return RepositoryImpl.getRepositoryInstance(sessionPreference)
    }
}