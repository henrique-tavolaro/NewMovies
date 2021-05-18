package com.example.newmovies.business.data.cache

import com.example.newmovies.framework.datasource.cache.model.CachedMovie
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie

class AppDatabaseFake {

    val cachedMovieList = mutableListOf<CachedMovie>()

    val cachedMovieDetails = mutableListOf<CachedMovieDetail>()

    val savedMovie = mutableListOf<SavedMovie>()

}