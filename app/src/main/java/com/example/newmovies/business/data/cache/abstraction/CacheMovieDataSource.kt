package com.example.newmovies.business.data.cache.abstraction

import com.example.newmovies.framework.datasource.cache.model.SavedMovie

interface CacheMovieDataSource {

    suspend fun insertCachedMovieList()
    //TODO criar model e mappers para salvar no cache antes de emitir

    suspend fun insertMovieToWatchList(movie: SavedMovie)

    suspend fun insertMovieAsWatched(movie: SavedMovie)

    suspend fun updateMovieToWatchList(movie: SavedMovie)

    suspend fun updateMovieAsWatched(movie: SavedMovie)

    suspend fun getAllMovies(): List<SavedMovie>

    suspend fun getSavedMovie(imdbId: String): SavedMovie
}