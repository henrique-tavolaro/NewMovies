package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.state.DataState
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.cache.mappers.SavedMovieMapper
import com.example.newmovies.framework.datasource.network.mappers.DetailResponseMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetMovieDetails(
    private val networkMovieDataSource: NetworkMovieDataSource,
    private val cacheMovieDataSource: CacheMovieDataSource,
    private val detailResponseMapper: DetailResponseMapper,
    private val cacheMovieDetailMapper: CachedMovieDetailsMapper,
    private val savedMovieMapper: SavedMovieMapper
) {

    fun execute(
        imdbId: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<MovieDetailResponse>> = flow {
        try {
            emit(DataState.loading())

            delay(1500)
            //get movie detail and covert to model
            if(isNetworkAvailable){
                val movieDetail = getMovieDetailFromNetwork(imdbId)

                //convert into entity and insert into cash
                val cacheMovieDetail = cacheMovieDetailMapper.responseListToEntityList(movieDetail)
                cacheMovieDataSource.insertCachedMovieDetail(cacheMovieDetail)
//            cacheMovieDataSource.insertMovieToWatchList(savedMovieMapper.responseListToEntityList(movieDetail))
                //query the cache and emit
                val cacheResult = cacheMovieDataSource.getMovieDetailsFromCache(imdbId)

                if(cacheResult != null){
                    emit(DataState.success(cacheResult))
                }
            } else {
                throw Exception("Unable to connect to the internet")
            }



        } catch (e: Exception) {
            emit(DataState.error<MovieDetailResponse>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getMovieDetailFromNetwork(imdbId: String): MovieDetailResponse {
        return detailResponseMapper.responseListToEntityList(
            networkMovieDataSource.getMovieDetails(
                imdbId
            )!!
        )
    }
}