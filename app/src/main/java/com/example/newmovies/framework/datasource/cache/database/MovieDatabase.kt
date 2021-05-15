package com.example.newmovies.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newmovies.framework.datasource.cache.model.SavedMovie

@Database(
    entities = [
        SavedMovie::class
    ], version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun getDao(): MovieDao
}