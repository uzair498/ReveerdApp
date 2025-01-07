package com.example.reveerdapp.api.signUpApi

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApiInterface {
    @POST("auth/signup")
    fun signUp(@Body request: SignUpApiDataModel): Call<ResponseBody>
}