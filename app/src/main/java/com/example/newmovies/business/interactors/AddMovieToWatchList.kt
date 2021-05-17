package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.state.DataState
import com.example.newmovies.framework.datasource.cache.mappers.SavedMovieMapper
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddMovieToWatchList(
    private val cacheMovieDataSource: CacheMovieDataSource,
    private val savedMovieMapper: SavedMovieMapper
) {

    fun execute(
        imdbId: String
    ): Flow<DataState<SavedMovie>> = flow {
        try {
            emit(DataState.loading())

            //get movie from cache
            val cacheResult = cacheMovieDataSource.getMovieDetailsFromCache(imdbId)
            val movieToSave = savedMovieMapper.responseListToEntityList(cacheResult)
            movieToSave.onToWatchList = true

            // convert to saved movie insert into saved movie
            cacheMovieDataSource.insertMovieToWatchList(movieToSave)

            emit(DataState.success(savedMovieMapper.responseListToEntityList(cacheResult)))

        } catch (e: Exception) {
            emit(DataState.error<SavedMovie>(e.message ?: "Unknown Error"))
        }
    }
}