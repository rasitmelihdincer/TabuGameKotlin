package com.example.tabugamekotlin.Model

import com.google.gson.annotations.SerializedName


data class Model (
    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
)