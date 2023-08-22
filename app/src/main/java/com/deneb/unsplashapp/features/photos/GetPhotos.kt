package com.deneb.unsplashapp.features.photos

import com.deneb.unsplashapp.core.interactor.UseCase
import com.deneb.unsplashapp.features.photos.model.UnsplashItemView
import javax.inject.Inject

class GetPhotos
@Inject constructor(private val photosRepository: PhotosRepository): UseCase<List<UnsplashItemView>, Params>() {
    override suspend fun run(params: Params) = photosRepository.photos(params.id)
}