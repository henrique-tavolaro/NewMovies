package com.example.newmovies.business.domain.model

import android.os.Parcelable
import com.example.newmovies.framework.datasource.network.model.Rating
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailResponse(

    val actors: String,
    val awards: String,
    val boxOffice: String,
    val country: String,
    val dVD: String,
    val director: String,
    val genre: String,
    val imdbID: String,
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

): Parcelable
