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
//{
//    override fun equals(other: Any?): Boolean {
//        if(this === other) return true
//
//        other as SavedMovie
//
//        if(imdbID != other.imdbID) return false
//        if(actors != other.actors) return false
//        if(director != other.director) return false
//        if(genre != other.genre) return false
//        if(imdbRating != other.imdbRating) return false
//        if(plot != other.plot) return false
//        if(title != other.title) return false
//        if(year != other.year) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = imdbID.hashCode()
//        result = 30 * result + title.hashCode()
//        result = 31 * result + actors.hashCode()
//        result = 31 * result + director.hashCode()
//        result = 31 * result + genre.hashCode()
//        result = 31 * result + imdbRating.hashCode()
//        result = 31 * result + plot.hashCode()
//        result = 31 * result + poster.hashCode()
//        result = 31 * result + year.hashCode()
//        result = 31 * result + onToWatchList.hashCode()
//        result = 31 * result + watched.hashCode()
//        result = 31 * result + myRating.hashCode()
//
//        return result
//    }
//}
