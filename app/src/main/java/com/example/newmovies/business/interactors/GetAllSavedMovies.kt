package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.domain.state.DataState
import com.example.newmovies.framework.datasource.cache.model.SavedMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllSavedMovies(
    private val cacheMovieDataSource: CacheMovieDataSource
) {

    fun execute(): Flow<DataState<List<SavedMovie>>> = flow {
        try {
            emit(DataState.loading())

            val savedMovieList = cacheMovieDataSource.getAllMovies()
            if(savedMovieList.isNotEmpty()){
                try{
                    emit(DataState.success(savedMovieList))
                } catch (e: Exception){
                    emit(DataState.error<List<SavedMovie>>(e.message ?: "Unknown Error"))
                }
            }



        } catch (e: Exception) {
            emit(DataState.error<List<SavedMovie>>(e.message ?: "Unknown Error"))
        }

    }
}
