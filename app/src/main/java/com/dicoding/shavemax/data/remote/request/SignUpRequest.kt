package com.dicoding.shavemax.data.remote.request

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val gender: String
)