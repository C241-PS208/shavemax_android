package com.dicoding.shavemax.ui.savedhairstyle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.shavemax.data.local.room.SavedHairstyle
import com.dicoding.shavemax.data.repository.HairRepositoryImpl
import com.dicoding.shavemax.utils.ResultState

class SavedHairstyleViewModel(private val hairRepositoryImpl: HairRepositoryImpl) : ViewModel() {
    val savedHairstyle: LiveData<ResultState<List<SavedHairstyle>>> = hairRepositoryImpl.getSavedHairstyles()
}