package com.example.newmovies.framework.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.interactors.SearchMovie
import com.example.newmovies.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val searchMovie: SearchMovie
) : ViewModel() {

    val loading = mutableStateOf(false)

    val movieList: MutableState<List<MovieResponse>> = mutableStateOf(listOf())



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