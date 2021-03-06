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

    override suspend fun insertCachedMovieDetail(cachedMovieDetail: CachedMovieDetail): Long {
        movieDao.insertCacheMovieDetail(cachedMovieDetail)
        return 1
    }

    override suspend fun insertCachedMovieList(cachedMovieList: List<CachedMovie>): LongArray {
        movieDao.insertCacheMovie(cachedMovieList)
        return longArrayOf(1)
    }

    override suspend fun getMovieFromCache(query: String): List<MovieResponse> {
        return movieDao.getMovieFromCache(query)
    }

    override suspend fun getMovieDetailsFromCache(imdbId: String): MovieDetailResponse {
        return movieDao.getMovieDetailFromCache(imdbId)
    }

    override suspend fun insertMovieToWatchList(movie: SavedMovie): Long {
        movieDao.insertMovieToWatchList(movie)
        return 1
    }

    override suspend fun insertMovieAsWatched(movie: SavedMovie): Long {
        movieDao.insertMovieAsWatched(movie)
        return 1
    }

    override suspend fun updateMovieToWatchList(movie: SavedMovie): Long {
        movieDao.updateMovieToWatchList(movie)
        return 1
    }

    override suspend fun updateMovieAsWatched(movie: SavedMovie): Long {
        movieDao.updateMovieAsWatched(movie)
        return 1
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