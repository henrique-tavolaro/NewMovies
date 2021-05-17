package com.example.newmovies.business.data.network.abstraction

import com.example.newmovies.framework.datasource.network.model.DetailResponse
import com.example.newmovies.framework.datasource.network.model.Search

interface NetworkMovieDataSource {

    suspend fun getMovie(query: String) : Search

    suspend fun getMovieDetails (imdbID: String) : DetailResponse
}