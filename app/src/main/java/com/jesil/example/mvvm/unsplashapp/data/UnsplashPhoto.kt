package com.jesil.example.mvvm.unsplashapp.data

/**
 * Created by @author [jesil toborowei]
 * */

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Data class for converting unsplash JSON
 * object to kotlin data class
 * */

@Parcelize
data class UnsplashPhoto(
    @SerializedName("id")
    val unsplashId: String,

    @SerializedName("description")
    val unsplashDescription: String?,

    @SerializedName("urls")
    val unsplashUrls: UnsplashPhotoUrls,

    @SerializedName("user")
    val unsplashUsers : UnsplashUser

): Parcelable {

    @Parcelize
    data class UnsplashPhotoUrls(
        @SerializedName("raw")
        val unsplashPhotoUrlsRaw: String,

        @SerializedName("full")
        val unsplashPhotoUrlsFull: String,

        @SerializedName("regular")
        val unsplashPhotoUrlsRegular: String,

        @SerializedName("small")
        val unsplashPhotoUrlsSmall: String,

        @SerializedName("thumb")
        val unsplashPhotoUrlsThumb: String,

    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        @SerializedName("name")
        val unsplashUserName: String,

        @SerializedName("userName")
        val unsplashUserUserName: String

    ) : Parcelable {

        /** this link is recommended for the website */

        val attributionUrl get() = "https://unsplash.com/$unsplashUserUserName?utm_source=ImageSearchApp&utm_medium=referral"
    }

}