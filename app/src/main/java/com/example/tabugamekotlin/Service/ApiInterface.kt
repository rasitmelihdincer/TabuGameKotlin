package com.example.tabugamekotlin.Service

import com.example.tabugamekotlin.Model.Data
import com.example.tabugamekotlin.Model.Model
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("api/cards")
    fun getData() : Call<Model>
}