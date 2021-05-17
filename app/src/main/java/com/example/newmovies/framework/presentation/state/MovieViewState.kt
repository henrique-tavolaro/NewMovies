package com.example.newmovies.framework.presentation.state

import android.os.Parcelable
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.domain.state.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieViewState (

    var movieList: ArrayList<MovieResponse>? = null

        ): Parcelable, ViewState