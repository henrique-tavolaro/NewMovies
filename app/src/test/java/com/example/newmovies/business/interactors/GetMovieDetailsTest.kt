package com.example.newmovies.business.interactors

import com.example.newmovies.business.data.cache.AppDatabaseFake
import com.example.newmovies.business.data.cache.FakeCacheMovieDataSourceImpl
import com.example.newmovies.business.data.network.MockWebServerResponses
import com.example.newmovies.business.data.network.MockWebServerResponses.movieDetailResponse
import com.example.newmovies.business.data.network.abstraction.NetworkMovieDataSource
import com.example.newmovies.business.data.network.implementation.NetworkMovieDataSourceImpl
import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.framework.datasource.cache.mappers.CacheMovieMapper
import com.example.newmovies.framework.datasource.cache.mappers.CachedMovieDetailsMapper
import com.example.newmovies.framework.datasource.cache.mappers.SavedMovieMapper
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetMovieDetailsTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val DUMMY_QUERY = "batman"

    //system in test
    private lateinit var getMovieDetails: GetMovieDetails

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

        //instantiate the system in test
        getMovieDetails = GetMovieDetails(
            networkMovieDataSource = fakeNetworkMovieDataSource,
            cacheMovieDataSource = fakeCacheMovieDataSourceImpl,
            cacheMovieDetailMapper = cachedMovieDetailsMapper,
            detailResponseMapper = detailResponseMapper,
            savedMovieMapper = savedMovieMapper
        )
    }

    @Test
    fun getMovieDetailFromNetwork_emitFromCache() = runBlocking{

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(movieDetailResponse)
        )

//        assert(fakeCacheMovieDataSourceImpl.getMovieDetailsFromCache(DUMMY_QUERY).equals())

        val flowItems = getMovieDetails.execute(DUMMY_QUERY).toList()

        val movieDetail = flowItems[1].data

        assert(movieDetail is MovieDetailResponse)
    }

    @AfterEach
    fun tearDown(){
        mockWebServer.shutdown()
    }

}