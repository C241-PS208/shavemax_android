package com.dicoding.hairstyler.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.utils.ResultState

interface HairRepository {
    fun getAllHairstyle() : LiveData<ResultState<List<HairstyleResponseItem>>>
}