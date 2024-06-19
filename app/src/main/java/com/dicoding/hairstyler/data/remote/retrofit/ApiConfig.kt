package com.dicoding.hairstyler.data.remote.retrofit

import com.dicoding.hairstyler.BuildConfig
import com.dicoding.hairstyler.data.local.preference.SessionPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {

        private const val BASE_URL_ONE = BuildConfig.BASE_URL_ONE
        private const val BASE_URL_TWO = BuildConfig.BASE_URL_TWO

        private fun createOkHttpClient(sessionPreference: SessionPreference): OkHttpClient {
            val user = runBlocking { sessionPreference.getToken().first() }
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", user.token)
                    .build()
                chain.proceed(requestHeaders)
            }

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }

        fun getApiServiceOne(sessionPreference: SessionPreference): ApiService {
            val client = createOkHttpClient(sessionPreference)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_ONE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiServiceTwo(sessionPreference: SessionPreference): ApiService {
            val client = createOkHttpClient(sessionPreference)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_TWO)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
