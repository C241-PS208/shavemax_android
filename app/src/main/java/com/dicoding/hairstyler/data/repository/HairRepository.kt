package com.dicoding.hairstyler.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.hairstyler.data.remote.response.HairstyleResponse
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.utils.ResultState

interface HairRepository {

    suspend fun getHairstyles() : Result<List<HairstyleResponseItem>>
}