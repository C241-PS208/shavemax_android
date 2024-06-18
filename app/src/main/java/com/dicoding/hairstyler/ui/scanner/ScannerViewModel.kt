package com.dicoding.hairstyler.ui.scanner

import androidx.lifecycle.ViewModel
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import java.io.File

class ScannerViewModel (private val repositoryImpl: UserRepositoryImpl) : ViewModel() {
    fun predict(image : File) = repositoryImpl.predict(image)
}