package com.example.newmovies.framework.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_movie_details")
data class CachedMovieDetail(
    @PrimaryKey(autoGenerate = false)
    val imdbID: String,
    val actors: String,
    val awards: String,
    val boxOffice: String,
    val country: String,
    val dVD: String,
    val director: String,
    val genre: String,
    val imdbRating: String,
    val imdbVotes: String,
    val language: String,
    val metascore: String,
    val plot: String,
    val poster: String,
    val production: String,
    val rated: String,
    val released: String,
    val response: String,
    val runtime: String,
    val title: String,
    val type: String,
    val website: String,
    val writer: String,
    val year: String
)