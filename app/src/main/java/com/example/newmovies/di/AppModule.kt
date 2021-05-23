package com.example.newmovies.di

import android.content.Context
import androidx.room.Room
import com.example.newmovies.business.data.cache.abstraction.CacheMovieDataSource
import com.example.newmovies.business.data.cache.implementation.CacheMovieDataSourceImpl
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.data.network.implementation.NetworkMovieDataSourceImpl
import com.example.newmovies.framework.datasource.cache.database.MovieDao
import com.example.newmovies.framework.datasource.cache.database.MovieDatabase
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.cache.mappers.SavedMovieMapper
import com.example.newmovies.framework.datasource.network.implementation.RetrofitService
import com.example.newmovies.framework.datasource.network.mappers.DetailResponseMapper
import com.example.newmovies.framework.datasource.network.mappers.ResponseMapper
import com.example.newmovies.framework.presentation.BaseApplication
import com.example.newmovies.util.BASE_URL
import com.example.newmovies.util.DATABASE
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideImdbService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideCacheMovieDetailsMapper(): CachedMovieDetailsMapper {
        return CachedMovieDetailsMapper()
    }

    @Singleton
    @Provides
    fun provideSavedMovieMapper(): SavedMovieMapper {
        return SavedMovieMapper()
    }

    @Singleton
    @Provides
    fun provideCacheMovieMapper(): CacheMovieMapper {
        return CacheMovieMapper()
    }

    @Singleton
    @Provides
    fun provideDetailResponseMapper(): DetailResponseMapper {
        return DetailResponseMapper()
    }

    @Singleton
    @Provides
    fun provideResponseMapper(): ResponseMapper {
        return ResponseMapper()
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.getDao()

    @Singleton
    @Provides
    fun provideCacheMovieDataSource(
        movieDao: MovieDao
    ) : CacheMovieDataSource {
        return CacheMovieDataSourceImpl(movieDao)
    }

    @Singleton
    @Provides
    fun provideNetworkMovieDataSource(
        retrofitService: RetrofitService
    ) : NetworkMovieDataSource {
        return NetworkMovieDataSourceImpl(retrofitService)
    }

}