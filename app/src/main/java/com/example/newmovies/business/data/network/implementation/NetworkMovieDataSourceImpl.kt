package com.example.newmovies.business.data.network.implementation

import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.framework.datasource.network.implementation.RetrofitService
import com.example.newmovies.framework.datasource.network.model.DetailResponse
import com.example.newmovies.framework.datasource.network.model.Search
import javax.inject.Inject

class NetworkMovieDataSourceImpl
@Inject constructor(
    private val retrofitService: RetrofitService
) : NetworkMovieDataSource {

    override suspend fun getMovie(query: String): Search {
        return retrofitService.getMovies(search = query)
    }

    override suspend fun getMovieDetails(imdbID: String): DetailResponse {
        return retrofitService.getMovieDetails(imdbID = imdbID)
    }
}