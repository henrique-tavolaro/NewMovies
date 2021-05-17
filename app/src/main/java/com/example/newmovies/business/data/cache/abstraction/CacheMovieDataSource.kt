package com.example.newmovies.business.data.cache.abstraction

import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.framework.datasource.cache.model.CachedMovie
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie

interface CacheMovieDataSource {

    suspend fun insertCachedMovieDetail(cachedMovieDetail: CachedMovieDetail)

    suspend fun insertCachedMovieList(cachedMovieList: List<CachedMovie>)

    suspend fun getMovieFromCache(query: String): List<MovieResponse>

    suspend fun getMovieDetailsFromCache(imdbId: String): MovieDetailResponse

    suspend fun insertMovieToWatchList(movie: SavedMovie)

    suspend fun insertMovieAsWatched(movie: SavedMovie)

    suspend fun updateMovieToWatchList(movie: SavedMovie)

    suspend fun updateMovieAsWatched(movie: SavedMovie)

    suspend fun getAllMovies(): List<SavedMovie>

    suspend fun getSavedMovie(imdbId: String): SavedMovie
}