package com.example.newmovies.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val searches: List<Response>,
    val totalResults: String,
    @SerializedName("Error")
    val error: String

)