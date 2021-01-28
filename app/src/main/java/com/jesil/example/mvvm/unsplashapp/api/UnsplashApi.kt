package com.jesil.example.mvvm.unsplashapp.api

import com.jesil.example.mvvm.unsplashapp.BuildConfig
import com.jesil.example.mvvm.unsplashapp.constant.Constants.UNSPLASH_BASE_URL
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    /**
     * static constance for building the
     * @GET and @Headers suspend functions for
     * the interface UnsplashApi
     * */

    companion object{
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
        const val BASE_URL = UNSPLASH_BASE_URL
    }

    /**
     * suspend functions for getting the
     * photos and returns the the list of UnsplashResponse,
     * */

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : UnsplashResponse
}