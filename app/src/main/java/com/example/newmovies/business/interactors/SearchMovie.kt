package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.domain.state.*
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.network.mappers.ResponseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception


class SearchMovie(
    private val networkMovieDataSource: NetworkMovieDataSource,
    private val cacheMovieDataSource: CacheMovieDataSource,
    private val responseMapper: ResponseMapper,
    private val cacheMovieMapper: CacheMovieMapper
) {

    fun execute(
        query: String
    ): Flow<DataState<List<MovieResponse>>> = flow {
        try {
            emit(DataState.loading())

            var cache: List<MovieResponse> = listOf()

            // check if movie is in cache
            val movieListFromCache = cacheMovieDataSource.getMovieFromCache(query)
            if (movieListFromCache.isNotEmpty()){
                cache = movieListFromCache
            } else {
                // get the list of movies from network and convert to model
                val movieList = getMoviesFromNetwork(query)

                // convert into entity and insert into the cache
                cacheMovieDataSource.insertCachedMovieList(
                    cacheMovieMapper.responseListToEntityList(
                        movieList
                    )
                )

                //query the cache and emit
                cache = cacheMovieDataSource.getMovieFromCache(query)
            }

            emit(DataState.success(cache))

        } catch (e: Exception) {
            emit(DataState.error<List<MovieResponse>>(e.message ?: "Unknown Error"))
        }

    }


    private suspend fun getMoviesFromNetwork(
        query: String
    ): List<MovieResponse> {
        return responseMapper.responseListToEntityList(
            networkMovieDataSource.getMovie(query).searches
        )
    }

}

//        val networkResult = safeApiCall(IO){
//            networkMovieDataSource.getMovie(query).searches
//        }
//
//        val networkResponse = object: ApiResponseHandler<MovieViewState, List<Response>>(
//            response = networkResult
//        ){
//            override suspend fun handleSuccess(resultObj: List<Response>): DataState<MovieViewState>? {
//                return DataState.data(
//                        response = StateResponse(
//                            message = SEARCH_SUCCESS,
//                            uiComponentType = UIComponentType.None(),
//                            messageType = MessageType.Success()
//                        ),
//                        data = MovieViewState(
//                            movieList = ArrayList(responseMapper.responseListToEntityList(resultObj))
//                        ),
//                        stateEvent = stateEvent
//                    )
//            }
//        }.getResult()
//
//        emit(networkResponse)
//
//
//
//        var cacheResult =  safeCacheCall(Dispatchers.IO){
//
//        }
