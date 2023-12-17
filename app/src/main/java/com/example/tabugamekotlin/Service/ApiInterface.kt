package com.example.tabugamekotlin.Service

import com.example.tabugamekotlin.Model.Model
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("nejdetkadir/turkish-taboo-words/main/data/words.json")
    fun getData() : Call<List<Model>>
}