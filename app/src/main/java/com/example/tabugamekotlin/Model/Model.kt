package com.example.tabugamekotlin.Model

import com.google.gson.annotations.SerializedName


import com.fasterxml.jackson.annotation.JsonProperty



data class Model (
    @SerializedName("word"            ) var word           : String?           = null,
    @SerializedName("forbidden_words" ) var forbiddenWords : ArrayList<String>? = arrayListOf(),
)
