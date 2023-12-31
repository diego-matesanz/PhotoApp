package com.deneb.unsplashapp.features.photos

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.deneb.unsplashapp.core.exception.Failure
import com.deneb.unsplashapp.core.extensions.failure
import com.deneb.unsplashapp.core.extensions.observe
import com.deneb.unsplashapp.core.platform.BaseFragment
import com.deneb.unsplashapp.databinding.FragmentPhotosBinding
import com.deneb.unsplashapp.features.photos.model.UnsplashItemView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {

    @Inject
    lateinit var photosAdapter: PhotosAdapter

    private val photosViewModel: PhotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(photosViewModel) {
            observe(photos, ::renderPhotoList)
            failure(failure, ::renderFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadPhotoList()
        setRecyclerViewScrollListener()
    }

    private fun initializeView() {
        binding.photoList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.photoList.adapter = photosAdapter
        photosAdapter.clickListener = { photo ->
            findNavController().navigate(PhotosFragmentDirections.actionPhotosFragmentToDetailPhotoFragment(photo.id))
        }
    }

    private fun loadPhotoList() {
        showProgress()
        photosViewModel.loadPhotos()
    }

    private fun renderPhotoList(photos: List<UnsplashItemView>?) {
        val mutableList = mutableListOf<UnsplashItemView>()
        mutableList.addAll(photosAdapter.collection)
        mutableList.addAll(photos.orEmpty())
        photosAdapter.collection = mutableList
        hideProgress()
    }

    private fun renderFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> Log.e(
                PhotosFragment::class.java.canonicalName,
                "Network Connection Error"
            )

            is Failure.ServerError -> Log.e(PhotosFragment::class.java.canonicalName, "Server Error")
            else -> Log.e(PhotosFragment::class.java.canonicalName, "Error")
        }
    }

    private fun setRecyclerViewScrollListener() {
        binding.photoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.photoList.canScrollVertically(1)) {
                    Log.d(PhotosFragment::class.java.canonicalName, "Load new photos")
                    loadPhotoList()
                }
            }
        })
    }

}