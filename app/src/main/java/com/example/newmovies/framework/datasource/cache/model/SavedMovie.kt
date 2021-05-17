package com.example.newmovies.framework.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_movie")
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
    var onToWatchList: Boolean,
    var watched: Boolean,
    val myRating: Float

)
