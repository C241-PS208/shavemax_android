package com.dicoding.shavemax.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInSuccessResponse(

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String
)
