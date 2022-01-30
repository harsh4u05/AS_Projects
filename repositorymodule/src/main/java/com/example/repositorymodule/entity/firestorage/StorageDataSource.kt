package com.example.repositorymodule.entity.firestorage

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.repositorymodule.entity.entities.Images
import com.google.firebase.storage.StorageReference
import java.util.*
import javax.inject.Inject

//BaseStorageDataSource: to get return result as Resource<T>
class StorageDataSource @Inject constructor(private val storageMethods: StorageMethods): BaseStorageDataSource() {

    fun insertImages(images: List<Uri>) = storageMethods.insertAllImages(images)

    fun getImagesRefs(): LiveData<List<Images>> = storageMethods.getImagesReferences()
    fun getImageRef(): LiveData<StorageReference> = storageMethods.getImageReference()

}