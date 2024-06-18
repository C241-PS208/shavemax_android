package com.dicoding.hairstyler.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import com.dicoding.hairstyler.di.Injection
import com.dicoding.hairstyler.ui.home.HomeViewModel
class HairViewModelFactory(
    private val hairRepositoryImpl: HairRepositoryImpl,
    private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(hairRepositoryImpl, userRepositoryImpl) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HairViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): HairViewModelFactory {
            if (INSTANCE == null) {
                synchronized(HairViewModelFactory::class.java) {
                    INSTANCE = HairViewModelFactory(
                        Injection.provideHairRepository(context),
                        Injection.provideUserRepository(context),
                    )
                }
            }
            return INSTANCE as HairViewModelFactory
        }
    }
}