package com.example.newmovies.framework.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_movie")
data class CachedMovie(
    @PrimaryKey(autoGenerate = false)
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String
)