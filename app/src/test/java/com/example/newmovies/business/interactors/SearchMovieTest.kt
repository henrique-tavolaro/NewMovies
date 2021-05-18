package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.AppDatabaseFake
import com.example.newmovies.business.data.cache.FakeCacheMovieDataSourceImpl
import com.example.newmovies.business.data.network.MockWebServerResponses.responseSuccess
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.data.network.implementation.NetworkMovieDataSourceImpl
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.domain.state.DataState
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.cache.mappers.SavedMovieMapper
import com.example.newmovies.framework.datasource.network.implementation.RetrofitService
import com.example.newmovies.framework.datasource.network.mappers.DetailResponseMapper
import com.example.newmovies.framework.datasource.network.mappers.ResponseMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchMovieTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val DUMMY_QUERY = "batman"

    //system in test
    private lateinit var searchMovie: SearchMovie

    //Dependencies
    private lateinit var retrofitService: RetrofitService
    private lateinit var fakeCacheMovieDataSourceImpl: FakeCacheMovieDataSourceImpl
    private lateinit var fakeNetworkMovieDataSource: NetworkMovieDataSource
    private val responseMapper = ResponseMapper()
//    private val detailResponseMapper = DetailResponseMapper()
    private val cachedMovieDetailsMapper = CachedMovieDetailsMapper()
    private val cacheMovieMapper = CacheMovieMapper()
//    private val savedMovieMapper = SavedMovieMapper()

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

        //instantiate the system in test
        searchMovie = SearchMovie(
            networkMovieDataSource = fakeNetworkMovieDataSource,
            cacheMovieDataSource = fakeCacheMovieDataSourceImpl,
            responseMapper = responseMapper,
            cacheMovieMapper = cacheMovieMapper
        )
    }

    @Test
    fun getMovieFromNetwork_emitMovieFromCache() = runBlocking{

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseSuccess)
        )

        // confirm the cache is empty to start
        assert(fakeCacheMovieDataSourceImpl.getMovieFromCache(DUMMY_QUERY).isEmpty())

        val flowItems = searchMovie.execute(DUMMY_QUERY).toList()

//        // confirm the cache is no longer empty

//        assert(fakeCacheMovieDataSourceImpl.getMovieFromCache(DUMMY_QUERY).isNotEmpty())
//
        // first emission should be `loading`
//        assert(flowItems[0].loading)
//
//        // second emission should be the list of movies
//        val movieList = flowItems[1].data
//        assert(movieList?.size?: 0 > 0 )
//
//        // confirm they are actually movie objects
//        assert(movieList?.get(0) is MovieResponse)

        // confirm loading is no longer true
//        assert(!flowItems[1].loading)

    }


















    @AfterEach
    fun tearDown(){
        mockWebServer.shutdown()
    }

}