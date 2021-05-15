package com.example.newmovies.business.data.network.abstraction

interface NetworkMovieDataSource {

    suspend fun getMovie(query: String)

    suspend fun getMovieDetails (imdbID: String)
}