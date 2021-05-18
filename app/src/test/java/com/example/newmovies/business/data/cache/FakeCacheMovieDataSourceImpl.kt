package com.example.newmovies.business.data.cache

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.cache.model.CachedMovie
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie

class FakeCacheMovieDataSourceImpl constructor(
    private val appDatabaseFake: AppDatabaseFake,
    private val cacheMovieMapper: CacheMovieMapper,
    private val cachedMovieDetailsMapper: CachedMovieDetailsMapper
): CacheMovieDataSource {
    override suspend fun insertCachedMovieDetail(cachedMovieDetail: CachedMovieDetail): Long {
        appDatabaseFake.cachedMovieDetails.add(cachedMovieDetail)
        return 1
    }


    override suspend fun insertCachedMovieList(cachedMovieList: List<CachedMovie>): LongArray {
        appDatabaseFake.cachedMovieList.addAll(cachedMovieList)
        return longArrayOf(1)
    }

    override suspend fun getMovieFromCache(query: String): List<MovieResponse> {
        return cacheMovieMapper.entityListToResponseList(appDatabaseFake.cachedMovieList)
    }

    override suspend fun getMovieDetailsFromCache(imdbId: String): MovieDetailResponse {
        val response = appDatabaseFake.cachedMovieDetails.find { it.imdbID == imdbId }
        return cachedMovieDetailsMapper.entityListToResponseList(response!!)
    }

    override suspend fun insertMovieToWatchList(movie: SavedMovie): Long {
        appDatabaseFake.savedMovie.add(movie)
        return 1
    }

    override suspend fun insertMovieAsWatched(movie: SavedMovie): Long {
        appDatabaseFake.savedMovie.add(movie)
        return 1
    }

    override suspend fun updateMovieToWatchList(movie: SavedMovie): Long {
        appDatabaseFake.savedMovie.add(movie)
        return 1
    }

    override suspend fun updateMovieAsWatched(movie: SavedMovie): Long {
        appDatabaseFake.savedMovie.add(movie)
        return 1
    }

    override suspend fun getAllMovies(): List<SavedMovie> {
        return appDatabaseFake.savedMovie
    }

    override suspend fun getSavedMovie(imdbId: String): SavedMovie {
        return appDatabaseFake.savedMovie[0]
    }

    override suspend fun deleteSavedMovie(movie: SavedMovie) {
        appDatabaseFake.savedMovie.remove(movie)
    }
}