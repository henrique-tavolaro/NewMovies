package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.domain.state.DataState
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.network.mappers.DetailResponseMapper
import com.example.newmovies.framework.datasource.network.mappers.ResponseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetMovieDetails(
    private val networkMovieDataSource: NetworkMovieDataSource,
    private val cacheMovieDataSource: CacheMovieDataSource,
    private val detailResponseMapper: DetailResponseMapper,
    private val cacheMovieDetailMapper: CachedMovieDetailsMapper
) {

    fun execute(
        imdbId: String
    ): Flow<DataState<MovieDetailResponse>> = flow {
        try {
            emit(DataState.loading())

            //get movie detail and covert to model
            val movieDetail = getMovieDetailFromNetwork(imdbId)

            //convert into entity and insert into cash
            val cacheMovieDetail = cacheMovieDetailMapper.responseListToEntityList(movieDetail)
            cacheMovieDataSource.insertCachedMovieDetail(cacheMovieDetail)

            //query the cache and emit
            val cacheResult = cacheMovieDataSource.getMovieDetailsFromCache(imdbId)

            emit(DataState.success(cacheResult))

        } catch (e: Exception) {
            emit(DataState.error<MovieDetailResponse>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getMovieDetailFromNetwork(imdbId: String): MovieDetailResponse {
        return detailResponseMapper.responseListToEntityList(
            networkMovieDataSource.getMovieDetails(
                imdbId
            )
        )
    }
}