package com.example.newmovies.framework.datasource.cache.database

import androidx.room.*
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.framework.datasource.cache.model.CachedMovie
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCacheMovie(cachedMovieList: List<CachedMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCacheMovieDetail(cachedMovieDetail: CachedMovieDetail)

    @Query("SELECT * FROM cached_movie WHERE title LIKE '%' || :query || '%'")
    suspend fun getMovieFromCache(query: String) : List<MovieResponse>

    @Query("SELECT * FROM caches_movie_details WHERE imdbID = :imdbId")
    suspend fun getMovieDetailFromCache(imdbId: String): MovieDetailResponse

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieAsWatched(movie: SavedMovie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieToWatchList(movie: SavedMovie)

    @Update
    suspend fun updateMovieAsWatched(movie: SavedMovie)

    @Update
    suspend fun updateMovieToWatchList(movie: SavedMovie)

    @Transaction
    @Query("SELECT * FROM moviesDb")
    suspend fun getAllMovies() : List<SavedMovie>

    @Transaction
    @Query("SELECT * FROM moviesDb WHERE imdbID = :imdbId ")
    suspend fun getSavedMovie(imdbId: String): SavedMovie

}