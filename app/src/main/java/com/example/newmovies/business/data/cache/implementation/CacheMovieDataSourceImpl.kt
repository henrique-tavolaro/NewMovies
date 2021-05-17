package com.example.newmovies.business.data.cache.implementation

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.framework.datasource.cache.database.MovieDao
import com.example.newmovies.framework.datasource.cache.model.CachedMovie
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import javax.inject.Inject

class CacheMovieDataSourceImpl
@Inject constructor(
    private val movieDao: MovieDao
): CacheMovieDataSource{

    override suspend fun insertCachedMovieDetail(cachedMovieDetail: CachedMovieDetail) {
        movieDao.insertCacheMovieDetail(cachedMovieDetail)
    }

    override suspend fun insertCachedMovieList(cachedMovieList: List<CachedMovie>) {
        movieDao.insertCacheMovie(cachedMovieList)
    }

    override suspend fun getMovieFromCache(query: String): List<MovieResponse> {
        return movieDao.getMovieFromCache(query)
    }

    override suspend fun getMovieDetailsFromCache(imdbId: String): MovieDetailResponse {
        return movieDao.getMovieDetailFromCache(imdbId)
    }

    override suspend fun insertMovieToWatchList(movie: SavedMovie) {
        movieDao.insertMovieToWatchList(movie)
    }

    override suspend fun insertMovieAsWatched(movie: SavedMovie) {
        movieDao.insertMovieAsWatched(movie)
    }

    override suspend fun updateMovieToWatchList(movie: SavedMovie) {
        movieDao.updateMovieToWatchList(movie)
    }

    override suspend fun updateMovieAsWatched(movie: SavedMovie) {
        movieDao.updateMovieAsWatched(movie)
    }

    override suspend fun getAllMovies(): List<SavedMovie> {
        return movieDao.getAllSavedMovies()
    }

    override suspend fun getSavedMovie(imdbId: String): SavedMovie {
        return movieDao.getSavedMovie(imdbId)
    }

    override suspend fun deleteSavedMovie(movie: SavedMovie) {
        movieDao.deleteSavedMovie(movie)
    }
}