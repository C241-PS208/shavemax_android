package com.dicoding.hairstyler.data.remote.retrofit

import com.dicoding.hairstyler.data.remote.request.SignInRequest
import com.dicoding.hairstyler.data.remote.request.SignUpRequest
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.remote.response.ResultResponse
import com.dicoding.hairstyler.data.remote.response.SignInSuccessResponse
import com.dicoding.hairstyler.data.remote.response.SignUpSuccessResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/sign-up")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): SignUpSuccessResponse

    @Headers("Content-Type: application/json")
    @POST("auth/sign-in")
    suspend fun signIn(
        @Body signUpRequest: SignInRequest
    ): SignInSuccessResponse

    //endpoint blm dpt
    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part,
        @Part("gender") gender: RequestBody
    ) : ResultResponse

    @Headers("Content-Type: application/json")
    @GET("hairstyles/all")
    suspend fun getAllHairstyle(): List<HairstyleResponseItem>

}