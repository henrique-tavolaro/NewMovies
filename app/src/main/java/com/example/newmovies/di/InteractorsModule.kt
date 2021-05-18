package com.example.newmovies.di

import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.interactors.*
import com.example.newmovies.framework.datasource.cache.database.MovieDao
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.cache.mappers.SavedMovieMapper
import com.example.newmovies.framework.datasource.network.mappers.DetailResponseMapper
import com.example.newmovies.framework.datasource.network.mappers.ResponseMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchMovie(
        networkMovieDataSource: NetworkMovieDataSource,
        cacheMovieDataSource: CacheMovieDataSource,
        responseMapper: ResponseMapper,
        cacheMovieMapper: CacheMovieMapper,
        updateMovieAsWatched: UpdateMovieAsWatched
    ) : SearchMovie {
        return SearchMovie(
            networkMovieDataSource = networkMovieDataSource,
            cacheMovieDataSource = cacheMovieDataSource,
            responseMapper = responseMapper,
            cacheMovieMapper = cacheMovieMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMovieDetails(
        networkMovieDataSource: NetworkMovieDataSource,
        cacheMovieDataSource: CacheMovieDataSource,
        detailResponseMapper: DetailResponseMapper,
        cacheMovieDetailMapper: CachedMovieDetailsMapper,
        savedMovieMapper: SavedMovieMapper
    ) : GetMovieDetails {
        return GetMovieDetails(
            networkMovieDataSource = networkMovieDataSource,
            cacheMovieDataSource = cacheMovieDataSource,
            detailResponseMapper = detailResponseMapper,
            cacheMovieDetailMapper = cacheMovieDetailMapper,
            savedMovieMapper = savedMovieMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideAddMovieToWatchList(
        cacheMovieDataSource: CacheMovieDataSource,
        savedMovieMapper: SavedMovieMapper
    ): AddMovieToWatchList {
        return AddMovieToWatchList(
            cacheMovieDataSource = cacheMovieDataSource,
            savedMovieMapper = savedMovieMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideAddMovieAsWatched(
        cacheMovieDataSource: CacheMovieDataSource,
        savedMovieMapper: SavedMovieMapper
    ): AddMovieAsWatched {
        return AddMovieAsWatched(
            cacheMovieDataSource = cacheMovieDataSource,
            savedMovieMapper = savedMovieMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUpdateMovieAsWatched(
        cacheMovieDataSource: CacheMovieDataSource
    ): UpdateMovieAsWatched {
        return UpdateMovieAsWatched(
            cacheMovieDataSource = cacheMovieDataSource
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUpdateMovieToWatchList(
        cacheMovieDataSource: CacheMovieDataSource
    ): UpdateMovieToWatchList {
        return UpdateMovieToWatchList(
            cacheMovieDataSource = cacheMovieDataSource
        )
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteSavedMovie(
        cacheMovieDataSource: CacheMovieDataSource
    ): DeleteSavedMovie {
        return DeleteSavedMovie(
            cacheMovieDataSource = cacheMovieDataSource
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetSavedMovie(
        cacheMovieDataSource: CacheMovieDataSource
    ): GetSavedMovie {
        return GetSavedMovie(
            cacheMovieDataSource = cacheMovieDataSource
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetAllSavedMovies(
        cacheMovieDataSource: CacheMovieDataSource
    ): GetAllSavedMovies {
        return GetAllSavedMovies(
            cacheMovieDataSource = cacheMovieDataSource
        )
    }
}