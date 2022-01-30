package com.example.projectmodules01.ui.images

import androidx.lifecycle.ViewModel
import com.example.repositorymodule.entity.repository.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(repository: ImagesRepository): ViewModel() {

    val imagesRefs = repository.getImagesRefs()
}