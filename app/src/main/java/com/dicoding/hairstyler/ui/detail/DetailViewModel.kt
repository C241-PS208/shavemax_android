package com.dicoding.hairstyler.ui.detail

import androidx.lifecycle.ViewModel
import com.dicoding.hairstyler.data.local.room.SavedHairstyle
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl

class DetailViewModel (private val hairRepositoryImpl: HairRepositoryImpl) : ViewModel() {
    fun saveHairstyle(hairName: String, hairDesc: String, hairUrl: String) {
        val newHairstyle = SavedHairstyle(hairName, hairDesc, hairUrl)
        hairRepositoryImpl.insert(newHairstyle)
    }

    fun deleteHairstyle(hairName: String, hairDesc: String, hairUrl: String) {
        val newHairstyle = SavedHairstyle(hairName, hairDesc, hairUrl)
        hairRepositoryImpl.delete(newHairstyle)
    }

    fun checkSaved(name : String) = hairRepositoryImpl.checkSaved(name)
}