package com.dicoding.shavemax.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.shavemax.data.repository.UserRepositoryImpl
import com.dicoding.shavemax.di.Injection
import com.dicoding.shavemax.ui.account.AccountViewModel
import com.dicoding.shavemax.ui.authentication.login.LoginViewModel
import com.dicoding.shavemax.ui.authentication.register.RegisterViewModel
import com.dicoding.shavemax.ui.authentication.welcome.WelcomeViewModel
import com.dicoding.shavemax.ui.main.MainViewModel
import com.dicoding.shavemax.ui.news.NewsViewModel
import com.dicoding.shavemax.ui.result.ResultViewModel
import com.dicoding.shavemax.ui.scanner.ScannerViewModel

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
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(ScannerViewModel::class.java) -> {
                ScannerViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(repositoryImpl) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(repositoryImpl) as T
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