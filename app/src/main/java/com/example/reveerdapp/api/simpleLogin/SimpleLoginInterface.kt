package com.example.reveerdapp.api.simpleLogin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SimpleLoginInterface {
    @POST("auth/login")
    fun login(@Body request: SimpleLoginDataModel): Call<LoginResponse>
}