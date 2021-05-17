package com.example.newmovies.di

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.interactors.SearchMovie
import com.example.newmovies.framework.datasource.cache.database.MovieDao
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.network.mappers.ResponseMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchMovie(
        networkMovieDataSource: NetworkMovieDataSource,
        cacheMovieDataSource: CacheMovieDataSource,
        responseMapper: ResponseMapper,
        cacheMovieMapper: CacheMovieMapper
    ) : SearchMovie {
        return SearchMovie(
            networkMovieDataSource = networkMovieDataSource,
            cacheMovieDataSource = cacheMovieDataSource,
            responseMapper = responseMapper,
            cacheMovieMapper = cacheMovieMapper

        )
    }
}