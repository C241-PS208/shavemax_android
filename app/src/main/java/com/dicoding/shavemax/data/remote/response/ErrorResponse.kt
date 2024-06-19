package com.dicoding.shavemax.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("message")
	override val message: String,

	@field:SerializedName("timestamp")
	val timestamp: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
) : Exception(message)
