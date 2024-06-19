package com.dicoding.hairstyler.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.hairstyler.data.local.room.SavedHairstyle
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.utils.ResultState

interface HairRepository {
    fun getAllHairstyle() : LiveData<ResultState<List<HairstyleResponseItem>>>
    fun insert(hairstyle: SavedHairstyle)

    fun delete(hairstyle: SavedHairstyle)

    fun getSavedHairstyles(): LiveData<ResultState<List<SavedHairstyle>>>

    fun checkSaved(name : String): LiveData<SavedHairstyle>
}