package com.deneb.unsplashapp.features.photos

import com.deneb.unsplashapp.features.photos.model.UnsplashResponse
import com.deneb.unsplashapp.features.photos.model.UnsplashDetailResponse
import com.deneb.unsplashapp.utils.ConstantsUtil
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

internal interface PhotosApi {
    @GET
    fun photos(@Url url: String): Call<UnsplashResponse>

    @GET("photos/{id}/?client_id=${ConstantsUtil.UNSPLASH_API_KEY}")
    fun detailPhoto(@Path("id")id: String): Call<UnsplashDetailResponse>
}