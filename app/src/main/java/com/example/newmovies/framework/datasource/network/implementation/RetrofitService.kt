package com.example.newmovies.framework.datasource.network.implementation

import com.example.newmovies.framework.datasource.network.model.DetailResponse
import com.example.newmovies.framework.datasource.network.model.Search
import com.example.newmovies.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("/")
    suspend fun getMovies(
        @Query
            ("apikey") apikey: String = API_KEY,
        @Query
            ("s") search: String,
    ) : Search

    @GET("/")
    suspend fun getMovieDetails(
        @Query
            ("apikey") apikey: String = API_KEY,
        @Query
            ("i") imdbID: String
    ) : DetailResponse
}