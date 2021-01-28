package com.jesil.example.mvvm.unsplashapp.api

import com.google.gson.annotations.SerializedName
import com.jesil.example.mvvm.unsplashapp.data.UnsplashPhoto

/**
 * created by @author [Jesil Toborowei]
 * */

data class UnsplashResponse(

    /** data class returns list of results
     * */

    @SerializedName("results")
    val unsplashResult : List<UnsplashPhoto>

)