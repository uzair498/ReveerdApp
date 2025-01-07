package com.example.reveerdapp.api.signUpApi

data class SignUpApiDataModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val role: String,
    val deviceToken: String
)

data class SignUpResponse(
    val success: Boolean,
    val message: String,
)