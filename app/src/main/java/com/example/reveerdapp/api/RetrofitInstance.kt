package com.example.reveerdapp.api

import com.example.reveerdapp.api.signUpApi.SignUpApiInterface
import com.example.reveerdapp.api.simpleLogin.SimpleLoginInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://dev.reveerd.com/api/"


    val simpleApiLoginService: SimpleLoginInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SimpleLoginInterface::class.java)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    val signUpService: SignUpApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignUpApiInterface::class.java)
    }


}