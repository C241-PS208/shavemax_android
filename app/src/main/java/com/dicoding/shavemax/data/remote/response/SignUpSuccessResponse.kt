package com.dicoding.shavemax.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignUpSuccessResponse(

	@field:SerializedName("role")
	val role: Role,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)

data class Role(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("modifiedAt")
	val modifiedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
