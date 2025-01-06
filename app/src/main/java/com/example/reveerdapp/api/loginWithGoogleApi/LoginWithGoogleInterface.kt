package com.example.reveerdapp.api.loginWithGoogleApi

import retrofit2.Call
import retrofit2.http.POST

interface LoginWithGoogleInterface {
    @POST("api/auth/login/google")
    fun loginWithGoogle(): Call<List<LoginWithGoogleDataModel>>
}