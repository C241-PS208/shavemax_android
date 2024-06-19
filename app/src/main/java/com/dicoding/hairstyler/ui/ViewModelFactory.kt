package com.dicoding.hairstyler.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import com.dicoding.hairstyler.di.Injection
import com.dicoding.hairstyler.ui.account.AccountViewModel
import com.dicoding.hairstyler.ui.authentication.login.LoginViewModel
import com.dicoding.hairstyler.ui.authentication.register.RegisterViewModel
import com.dicoding.hairstyler.ui.home.HomeViewModel
import com.dicoding.hairstyler.ui.main.MainViewModel
import com.dicoding.hairstyler.ui.news.NewsViewModel
import com.dicoding.hairstyler.ui.result.ResultViewModel
import com.dicoding.hairstyler.ui.scanner.ScannerViewModel

class ViewModelFactory(
    private val repositoryImpl: UserRepositoryImpl,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(ScannerViewModel::class.java) -> {
                ScannerViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(repositoryImpl) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideUserRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}