package com.dicoding.hairstyler.ui.savedhairstyle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.hairstyler.data.local.room.SavedHairstyle
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl
import com.dicoding.hairstyler.utils.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedHairstyleViewModel(private val hairRepositoryImpl: HairRepositoryImpl) : ViewModel() {
    val savedHairstyle: LiveData<ResultState<List<SavedHairstyle>>> = hairRepositoryImpl.getSavedHairstyles()
}