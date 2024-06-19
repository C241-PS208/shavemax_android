package com.dicoding.hairstyler.ui.savedhairstyle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.hairstyler.data.local.room.SavedHairstyle
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl
import com.dicoding.hairstyler.utils.ResultState

class SavedHairstyleViewModel(private val hairRepositoryImpl: HairRepositoryImpl) : ViewModel() {
    val savedHairstyle: LiveData<ResultState<List<SavedHairstyle>>> = hairRepositoryImpl.getSavedHairstyles()
}