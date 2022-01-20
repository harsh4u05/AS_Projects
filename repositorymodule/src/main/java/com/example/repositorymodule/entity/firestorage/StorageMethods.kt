package com.example.repositorymodule.entity.firestorage

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

//implementing the methods
open class StorageMethods @Inject constructor(){

    //implementation 'com.google.firebase:firebase-storage-ktx:20.0.0'
    private val storageRef = Firebase.storage.reference

    fun insertAllImages(images: List<Uri>) {

        val imagesRef = storageRef.child("images/")

        for ( i in 0 until images.size ) {
            var uploadTask: UploadTask = imagesRef.putFile(images[i])

            uploadTask.addOnSuccessListener {
                Timber.d("-------> Success : Image upload to Storage complete: ${it.bytesTransferred}")
            }.addOnFailureListener {
                Timber.e("----->>> Failed: Image Upload to Storage failed : $it")
                throw it
            }
        }

    }

    fun getImagesReferences(): LiveData<List<StorageReference>> =

    liveData(Dispatchers.IO) {

        var _imagesList = MutableLiveData<List<StorageReference>>()
        var imagesList: LiveData<List<StorageReference>> = _imagesList

        val imagesRefs = arrayListOf<StorageReference>()
        imagesRefs.add(storageRef)
        Timber.d("-------- StorageMethods storageRef : $storageRef ")

//        for (image in filePath) {
//            Timber.d("------ In StorageMethods getImagesReferences() : filePath size -> ${filePath.size} ")
//            imagesRefs.add(storageRef.child(image))
//            Timber.d("------ In StorageMethods getImagesReferences() : imagesRefs size -> ${imagesRefs.size} ")
//        }

        _imagesList.postValue(imagesRefs)
        emitSource(imagesList)
    }


    //Code: https://firebase.google.com/docs/storage/android/download-files#downloading_images_with_firebaseui
    fun getImagesCode() {

        // Below code just for reference.
        // Create a child reference
        val pathReference: StorageReference? = storageRef.child("images/stars.jpg")

        //get a reference to a file from a Google Cloud Storage URI
        val gsReference = Firebase.storage.getReferenceFromUrl("gs://myproj-42de4.appspot.com/images")

        // Create a reference from an HTTPS URL
        // Note that in the URL, characters are URL escaped!
        val httpsReference = Firebase.storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg")
    }
}