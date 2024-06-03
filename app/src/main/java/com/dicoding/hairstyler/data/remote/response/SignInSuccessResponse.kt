package com.dicoding.hairstyler.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInSuccessResponse(

	@field:SerializedName("token")
	val token: String
)
