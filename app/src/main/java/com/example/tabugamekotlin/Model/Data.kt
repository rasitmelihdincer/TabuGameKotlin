package com.example.tabugamekotlin.Model

import com.google.gson.annotations.SerializedName

data class Data (

    @SerializedName("forbiddenWords" ) var forbiddenWords : ArrayList<String> = arrayListOf(),
    @SerializedName("title"          ) var title          : String?           = null,
    @SerializedName("difficulty"     ) var difficulty     : String?           = null

)