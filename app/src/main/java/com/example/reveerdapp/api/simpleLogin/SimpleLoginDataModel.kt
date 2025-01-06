package com.example.reveerdapp.api.simpleLogin

data class SimpleLoginDataModel(
    val deviceToken: String,
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String
)