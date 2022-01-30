package com.example.repositorymodule.entity.firestorage

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.repositorymodule.entity.entities.Images
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

   // private val storageRef = Firebase.storage.reference

    fun insertAllImages(images: List<Uri>) {

        val imagesRef = Firebase.storage.reference.child("images/")

        for ( i in 0 until images.size ) {
            val uploadTask: UploadTask = imagesRef.putFile(images[i])

            uploadTask.addOnSuccessListener {
                Timber.d("-------> Success : Image upload to Storage complete: ${it.bytesTransferred}")
            }.addOnFailureListener {
                Timber.e("----->>> Failed: Image Upload to Storage failed : $it")
                throw it
            }
        }

    }

    fun getImagesReferences(): LiveData<List<Images>> =

    liveData(Dispatchers.IO) {

        val _imagesList = MutableLiveData<List<Images>>()
        val imagesList: LiveData<List<Images>> = _imagesList

//        val tmpRef = storageRef.child("Cancelled_Cheque.jpg")
        var tmpRef: StorageReference? = null


            try {
                tmpRef = Firebase.storage.reference.child("MedicalReport.jpg")
            } catch (ex: Exception) {
                Timber.e("--------------- Harshh Storage Exception : ${ex.localizedMessage}")
            }



        val image = Images(imageUri = tmpRef.toString())
        //val image = Images(imageRef = storageRef)

        val imagesRefs = arrayListOf<Images>()
        for(i in 0..4) {
            if ( i > 4) {
                Timber.d("Value of i is: $i")
                break
            }
            imagesRefs.add(image)
        }
        Timber.d("-------- StorageMethods storageRef : ${Firebase.storage.reference} ")

//        for (image in filePath) {
//            Timber.d("------ In StorageMethods getImagesReferences() : filePath size -> ${filePath.size} ")
//            imagesRefs.add(storageRef.child(image))
//            Timber.d("------ In StorageMethods getImagesReferences() : imagesRefs size -> ${imagesRefs.size} ")
//        }

        _imagesList.postValue(imagesRefs)
        emitSource(imagesList)

    }

    fun getImageReference(): LiveData<StorageReference> =

        liveData(Dispatchers.IO) {

            val _imagesList = MutableLiveData<StorageReference>()
            val imagesList: LiveData<StorageReference> = _imagesList

            _imagesList.postValue(Firebase.storage.reference)
            emitSource(imagesList)
        }


    //Code: https://firebase.google.com/docs/storage/android/download-files#downloading_images_with_firebaseui
    fun getImagesCode() {

        // Below code just for reference.
        // Create a child reference
        val pathReference: StorageReference = Firebase.storage.reference.child("images/stars.jpg")

        //get a reference to a file from a Google Cloud Storage URI
        val gsReference = Firebase.storage.getReferenceFromUrl("gs://myproj-42de4.appspot.com/images")

        // Create a reference from an HTTPS URL
        // Note that in the URL, characters are URL escaped!
        val httpsReference = Firebase.storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg")
    }
}