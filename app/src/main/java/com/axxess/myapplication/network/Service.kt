package com.axxess.myapplication.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


/**
 * A retrofit service to fetch a images.
 */
interface SearchService {
    @Headers("Authorization: ${Constants.CLIENT_ID}")
    @GET(Constants.SEARCH_API)
    fun getSearchResults(@Query("page") pageNumber:String, @Query("q") qString:String): Deferred<NetworkImageContainer>
}

/**
 * Main entry point for network access. Call like `NetworkClient.articlesService.getArticles()`
 */
object NetworkClient {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val searchService = retrofit.create(SearchService::class.java)
}