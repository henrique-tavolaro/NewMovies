package com.example.newmovies.framework.presentation.state

import android.graphics.Movie
import com.example.newmovies.business.domain.state.StateEvent

sealed class MovieStateEvent: StateEvent {

    class SearchMoviesOnNetwork: MovieStateEvent(){
        override fun errorInfo(): String {
            return "Error searching movies"
        }

        override fun eventName(): String {
            return "SearchMoviesOnNetwork"
        }

        override fun shouldDisplayProgressBar(): Boolean {
            return true
        }
    }
}
