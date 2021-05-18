package com.example.newmovies.framework.presentation.state

sealed class MovieEvent {

    class AddMovieAsWatchedEvent(val imdbId: String): MovieEvent()

    class AddMovieToWatchListEvent(val imdbId: String): MovieEvent()

    class DeleteSavedMovieEvent(val imdbId: String): MovieEvent()

    object GetAllSavedMovieEvent : MovieEvent()

    class GetMovieDetailsEvent(val imdbId: String): MovieEvent()

    class GetSavedMovieEvent(val imdbId: String): MovieEvent()

    class SearchMovieEvent(val query: String): MovieEvent()

    class UpdateMovieAsWatchedEvent(val imdbId: String): MovieEvent()

    class UpdateMovieToWatchListEvent(val imdbId: String): MovieEvent()

}