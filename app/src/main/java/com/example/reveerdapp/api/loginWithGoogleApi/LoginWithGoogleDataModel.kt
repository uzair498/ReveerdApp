package com.example.reveerdapp.api.loginWithGoogleApi

data class LoginWithGoogleDataModel(
    val deviceToken: String,
    val email: String,
    val firstName: String,
    val googleId: String,
    val image: String,
    val lastName: String,
    val role: String
)