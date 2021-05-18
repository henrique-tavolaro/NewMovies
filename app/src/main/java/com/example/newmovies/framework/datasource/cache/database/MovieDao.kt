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
    suspend fun insertCacheMovie(cachedMovieList: List<CachedMovie>) : LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCacheMovieDetail(cachedMovieDetail: CachedMovieDetail) : Long

    @Query("SELECT * FROM cached_movie WHERE title LIKE '%' || :query || '%'")
    suspend fun getMovieFromCache(query: String) : List<MovieResponse>

    @Query("SELECT * FROM cached_movie_details WHERE imdbID = :imdbId")
    suspend fun getMovieDetailFromCache(imdbId: String): MovieDetailResponse

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieAsWatched(movie: SavedMovie) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieToWatchList(movie: SavedMovie) : Long

    @Update
    suspend fun updateMovieAsWatched(movie: SavedMovie) : Int

    @Update
    suspend fun updateMovieToWatchList(movie: SavedMovie) : Int

    @Delete
    suspend fun deleteSavedMovie(movie: SavedMovie) : Int

    @Transaction
    @Query("SELECT * FROM saved_movie")
    suspend fun getAllSavedMovies() : List<SavedMovie>

    @Transaction
    @Query("SELECT * FROM saved_movie WHERE imdbID = :imdbId ")
    suspend fun getSavedMovie(imdbId: String): SavedMovie

}