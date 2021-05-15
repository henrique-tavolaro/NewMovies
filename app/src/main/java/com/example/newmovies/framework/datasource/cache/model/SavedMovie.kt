package com.example.newmovies.framework.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesDb")
data class SavedMovie(
    @PrimaryKey(autoGenerate = false)
    val imdbID: String,
    val actors: String,
    val director: String,
    val genre: String,
    val imdbRating: String,
    val plot: String,
    val poster: String,
    val title: String,
    val year: String,
    val onToWatchList: Boolean,
    val watched: Boolean,
    val myRating: Float

)
