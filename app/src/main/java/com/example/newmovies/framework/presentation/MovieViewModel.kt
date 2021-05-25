package com.example.newmovies.framework.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.interactors.*
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import com.example.newmovies.framework.presentation.state.MovieEvent
import com.example.newmovies.framework.presentation.state.MovieStateEvent
import com.example.newmovies.util.ConnectivityManager
import com.example.newmovies.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

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
    private val getSavedMovie: GetSavedMovie,
    private val connectivityManager: ConnectivityManager

) : ViewModel() {

    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    val loading = mutableStateOf(false)

    val movieSearch = mutableStateOf("")

    fun onTextChange(text: String) {
        movieSearch.value = text
        getMovie(text)
    }

    init {
        onTriggerEvent(MovieEvent.GetAllSavedMovieEvent)
    }

    fun onTriggerEvent(event: MovieEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is MovieEvent.AddMovieAsWatchedEvent -> {
                        insertMovieAsWatched(event.imdbId)
                    }
                    is MovieEvent.AddMovieToWatchListEvent -> {
                        insertMovieToWatchList(event.imdbId)
                    }
                    is MovieEvent.DeleteSavedMovieEvent -> {
                        deleteSavedMovie(event.imdbId)
                    }
                    is MovieEvent.GetAllSavedMovieEvent -> {
                        getAllSaved()
                    }
                    is MovieEvent.GetMovieDetailsEvent -> {
                        getMovieDetails(event.imdbId)
                    }
                    is MovieEvent.GetSavedMovieEvent -> {
                        getSavedMovie(event.imdbId)

                    }
                    is MovieEvent.SearchMovieEvent -> {
                        getMovie(event.query)
                    }
                    is MovieEvent.UpdateMovieAsWatchedEvent -> {
                        updateMovieAsWatched(event.imdbId)
                    }
                    is MovieEvent.UpdateMovieToWatchListEvent -> {
                        updateMovieToWatchList(event.imdbId)
                    }
                }
            } catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    fun deleteSavedMovie(imdbId: String){
        deleteSavedMovie.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                Log.d(TAG, it.toString())
            }

            dataState.error?.let {
                Log.d(TAG, it)
            }

        }.launchIn(viewModelScope)
    }

    val savedMovie: MutableState<SavedMovie?> = mutableStateOf(null)

    fun getSavedMovie(imdbId: String){
        getSavedMovie.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                savedMovie.value = it
                Log.d("getSavedMovie", it.toString())
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")

            }
        }.launchIn(viewModelScope)
    }

        val getAllSaved: MutableState<List<SavedMovie>> = mutableStateOf(listOf())

    private fun getAllSaved(){
        getAllSavedMovies.execute().onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                getAllSaved.value = it
                Log.d(TAG, it.toString())
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")
//                Toast.makeText(requireContext(), "An error occurred: $error", Toast.LENGTH_LONG).show()
            }
        }.launchIn(viewModelScope)
    }

    val updateAsWatched: MutableState<SavedMovie?> = mutableStateOf(null)

    private fun updateMovieAsWatched(imdbId: String) {
        updateMovieAsWatched.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                updateAsWatched.value = it
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")
//                Toast.makeText(context, "Error updating movie: $error", Toast.LENGTH_LONG).show()

            }
        }.launchIn(viewModelScope)
    }

    val updateToWatchList: MutableState<SavedMovie?> = mutableStateOf(null)

    private fun updateMovieToWatchList(imdbId: String) {
        updateMovieToWatchList.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                updateToWatchList.value = it
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")
//                Toast.makeText(requireContext(), "Error updating movie: $error", Toast.LENGTH_LONG).show()

            }
        }.launchIn(viewModelScope)
    }

    val movieDetails: MutableState<MovieDetailResponse?> = mutableStateOf(null)

    private fun getMovieDetails(imdbId: String) {
        getMovieDetails.execute(imdbId, connectivityManager.isNetworkAvailable.value).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { data ->
                movieDetails.value = data
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")
//                Toast.makeText(requireContext(), "Error getting movie details: $error", Toast.LENGTH_LONG).show()

            }
        }.launchIn(viewModelScope)

    }

    val movieToSaveToWatchList: MutableState<SavedMovie?> = mutableStateOf(null)

    private fun insertMovieToWatchList(imdbId: String) {
        addMovieToWatchList.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                movieToSaveToWatchList.value = it
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")
//                Toast.makeText(requireContext(), "Error adding movie: $error", Toast.LENGTH_LONG).show()

            }
        }.launchIn(viewModelScope)
    }

    val movieSavedAsWatched: MutableState<SavedMovie?> = mutableStateOf(null)

    private fun insertMovieAsWatched(imdbId: String) {
        addMovieAsWatched.execute(imdbId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                movieSavedAsWatched.value = it
            }

            dataState.error?.let { error ->
                Log.d(TAG, "movieListError: $error")
//                Toast.makeText(requireContext(), "Error adding movie: $error", Toast.LENGTH_LONG).show()

            }
        }.launchIn(viewModelScope)
    }

    val movieList: MutableState<List<MovieResponse>> = mutableStateOf(listOf())

    private fun getMovie(query: String) {
            searchMovie.execute(query, connectivityManager.isNetworkAvailable.value).onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { data ->
                    Log.d(TAG, data.toString())
                    movieList.value = data
                }

                dataState.error?.let { error ->
                    Log.d(TAG, "movieListError: $error")
//                    Toast.makeText(requireContext(), "An error occurred: $error", Toast.LENGTH_LONG).show()
                    movieList.value = listOf()
                }
            }.launchIn(viewModelScope)
    }
}