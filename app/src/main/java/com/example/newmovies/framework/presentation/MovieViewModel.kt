package com.example.newmovies.framework.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.interactors.*
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import com.example.newmovies.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val searchMovie: SearchMovie,
    private val getMovieDetails: GetMovieDetails,
    private val addMovieToWatchList: AddMovieToWatchList,
    private val addMovieAsWatched: AddMovieAsWatched,
    private val updateMovieAsWatched: UpdateMovieAsWatched,
    private val updateMovieToWatchList: UpdateMovieToWatchList,
    private val deleteSavedMovie: DeleteSavedMovie,
    private val getAllSavedMovies: GetAllSavedMovies,
    private val getSavedMovie: GetSavedMovie

) : ViewModel() {

    val loading = mutableStateOf(false)

    val movieList: MutableState<List<MovieResponse>> = mutableStateOf(listOf())

    val movieToSave: MutableState<SavedMovie?> = mutableStateOf(null)

    val movieDetails: MutableState<MovieDetailResponse?> = mutableStateOf(null)

    fun getMovieDetails(imdbId: String) {
        getMovieDetails.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { data ->
                movieDetails.value = data
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")

            }
        }.launchIn(viewModelScope)

    }


    fun insertMovieToWatchList(imdbId: String) {
        addMovieToWatchList.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                movieToSave.value = it
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")

            }
        }.launchIn(viewModelScope)
    }

    fun getMovie(query: String) {
            searchMovie.execute(query).onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { data ->
                    Log.d(TAG, data.toString())
                    movieList.value = data
                }

                dataState.error?.let { error ->
                    Log.d(TAG, "movieListError: $error")
                    movieList.value = listOf()
                }
            }.launchIn(viewModelScope)

    }

}