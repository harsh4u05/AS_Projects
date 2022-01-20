package com.example.repositorymodule.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repositorymodule.entity.repository.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(repository: ImagesRepository): ViewModel() {

    val images = repository.getImagesRefs()
}

//class ImageViewModelFactory(private val repository: ImagesRepository): ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(ImageViewModel::class.java)) {
//            return ImageViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel")
//    }
//
//}