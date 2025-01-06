package com.example.reveerdapp.api

import com.example.reveerdapp.api.simpleLogin.SimpleLoginInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val SIMPLE_LOGIN_BASE_URL = "https://dev.reveerd.com/api/"


        val simpleApiLoginService: SimpleLoginInterface by lazy {
            Retrofit.Builder()
                .baseUrl(SIMPLE_LOGIN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SimpleLoginInterface::class.java)
        }



}