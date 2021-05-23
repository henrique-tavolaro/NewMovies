package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.domain.state.DataState
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateMovieToWatchList (
    private val cacheMovieDataSource: CacheMovieDataSource
) {

    fun execute(
        imdbId: String
    ): Flow<DataState<SavedMovie>> = flow {
        try {
            emit(DataState.loading())

            val savedMovie = cacheMovieDataSource.getSavedMovie(imdbId)

            savedMovie!!.onToWatchList = true

            cacheMovieDataSource.updateMovieAsWatched(savedMovie)

            emit(DataState.success(savedMovie))

        } catch (e: Exception) {
            emit(DataState.error<SavedMovie>(e.message ?: "Unknown Error"))
        }
    }
}