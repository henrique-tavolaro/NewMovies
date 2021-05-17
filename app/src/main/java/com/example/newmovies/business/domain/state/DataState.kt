package com.example.newmovies.business.domain.state

import com.example.newmovies.business.domain.model.MovieResponse


data class DataState<out T>(
    val error: String? = null,
    val data: T? = null,
    val loading: Boolean = false
) {

    companion object {

        fun <T> error(
            message: String,
        ): DataState<T>{
            return DataState(
                error = message
            )
        }

        fun <T> success(
            data: T
        ): DataState<T> {
            return DataState(
                data = data
            )
        }

        fun <T> loading(): DataState<T> = DataState(loading = true)
    }
}