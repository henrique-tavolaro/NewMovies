package com.example.newmovies.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newmovies.framework.datasource.cache.model.CachedMovie
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie

@Database(
    entities = [
        SavedMovie::class,
        CachedMovie::class,
        CachedMovieDetail::class
    ], version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun getDao(): MovieDao
}