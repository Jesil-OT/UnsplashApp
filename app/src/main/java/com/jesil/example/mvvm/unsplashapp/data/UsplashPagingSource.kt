package com.jesil.example.mvvm.unsplashapp.data

import androidx.paging.PagingSource
import com.jesil.example.mvvm.unsplashapp.api.UnsplashApi
import com.jesil.example.mvvm.unsplashapp.constant.Constants.UNSPLASH_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class UsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        // val position = UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(
                page = position,
                query = query,
                perPage = params.loadSize
            )
            val photos = response.unsplashResult

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        }
        catch (exception : IOException){
            LoadResult.Error(
                exception
            )
        }
        catch (httpException : HttpException){
            LoadResult.Error(
                httpException
            )
        }
    }

}