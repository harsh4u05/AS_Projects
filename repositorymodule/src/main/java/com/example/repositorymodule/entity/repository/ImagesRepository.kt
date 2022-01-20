package com.example.repositorymodule.entity.repository

import com.example.repositorymodule.entity.firestorage.StorageDataSource
import com.example.repositorymodule.utils.performGetOperation
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    private val storageDataSource: StorageDataSource) {

    fun getImagesRefs() = performGetOperation(
        storageQuery = {storageDataSource.getImagesRefs()}
    )

}