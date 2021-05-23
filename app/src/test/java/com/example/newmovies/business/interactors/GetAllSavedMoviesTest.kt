package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.AppDatabaseFake
import com.example.newmovies.business.data.cache.FakeCacheMovieDataSourceImpl
import com.example.newmovies.business.data.network.MockWebServerResponses
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.data.network.implementation.NetworkMovieDataSourceImpl
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.cache.mappers.SavedMovieMapper
import com.example.newmovies.framework.datasource.network.implementation.RetrofitService
import com.example.newmovies.framework.datasource.network.mappers.DetailResponseMapper
import com.example.newmovies.framework.datasource.network.mappers.ResponseMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

internal class GetAllSavedMoviesTest{
    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val IMDBID = "tt0096895"
    private lateinit var getMovieDetails: GetMovieDetails
    private lateinit var addMovieAsWatched: AddMovieAsWatched

    //system in test
    private lateinit var getAllSavedMovies: GetAllSavedMovies

    //Dependencies
    private lateinit var retrofitService: RetrofitService
    private lateinit var fakeCacheMovieDataSourceImpl: FakeCacheMovieDataSourceImpl
    private lateinit var fakeNetworkMovieDataSource: NetworkMovieDataSource
    private val responseMapper = ResponseMapper()
    private val detailResponseMapper = DetailResponseMapper()
    private val cachedMovieDetailsMapper = CachedMovieDetailsMapper()
    private val cacheMovieMapper = CacheMovieMapper()
    private val savedMovieMapper = SavedMovieMapper()


    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/")

        retrofitService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitService::class.java)

        fakeCacheMovieDataSourceImpl = FakeCacheMovieDataSourceImpl(
            appDatabaseFake = appDatabase,
            cacheMovieMapper = cacheMovieMapper,
            cachedMovieDetailsMapper = cachedMovieDetailsMapper
        )

        fakeNetworkMovieDataSource = NetworkMovieDataSourceImpl(retrofitService)

        //instantiate the system
        getMovieDetails = GetMovieDetails(
            networkMovieDataSource = fakeNetworkMovieDataSource,
            cacheMovieDataSource = fakeCacheMovieDataSourceImpl,
            cacheMovieDetailMapper = cachedMovieDetailsMapper,
            detailResponseMapper = detailResponseMapper,
            savedMovieMapper = savedMovieMapper
        )

        addMovieAsWatched = AddMovieAsWatched(
            cacheMovieDataSource = fakeCacheMovieDataSourceImpl,
            savedMovieMapper = savedMovieMapper
        )

        // instantiate the system in test
        getAllSavedMovies = GetAllSavedMovies(
            cacheMovieDataSource = fakeCacheMovieDataSourceImpl
        )

    }

    @Test
    fun addMovieToSavedList_getMovie() = runBlocking {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.movieDetailResponse)
        )

        //get movie from network and insert into cache
        getMovieDetails.execute(IMDBID).toList()

        // confirm cache is not empty and has the imdbId
        assert(fakeCacheMovieDataSourceImpl.getMovieDetailsFromCache(IMDBID).imdbID == IMDBID)

        // add movie to SavedList
        addMovieAsWatched.execute(IMDBID).toList()

        // confirm the movie was saved
        assert(fakeCacheMovieDataSourceImpl.getSavedMovie(IMDBID).imdbID == IMDBID)

        // get all movies

        val flowItem = getAllSavedMovies.execute().toList()

        // assert it got all movies

        assert(flowItem[1].data != null)


    }


    @AfterEach
    fun tearDown(){
        mockWebServer.shutdown()
    }
}