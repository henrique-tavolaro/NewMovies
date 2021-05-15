package com.example.newmovies.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(

    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String

) : Parcelable
