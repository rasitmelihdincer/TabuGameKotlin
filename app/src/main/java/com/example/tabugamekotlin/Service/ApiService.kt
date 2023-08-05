package com.example.tabugamekotlin.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiService {

    private lateinit var retrofit: Retrofit
    var BASE_URL = "https://gist.githubusercontent.com/"

    fun getApiInterface () : ApiInterface {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }
}