package com.deneb.unsplashapp.features.photos

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosService
@Inject constructor(retrofit: Retrofit): PhotosApi {

    private val photosApi by lazy { retrofit.create(PhotosApi::class.java) }

    override fun photos(url: String) = photosApi.photos(url)
    override fun detailPhoto(id: String) = photosApi.detailPhoto(id)

}