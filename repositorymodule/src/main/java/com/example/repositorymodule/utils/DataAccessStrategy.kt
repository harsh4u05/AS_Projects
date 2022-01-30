package com.example.repositorymodule.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

fun <T> performGetOperation(
    storageQuery: () -> LiveData<T>
    ): LiveData<Resource<T>> =

    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = storageQuery.invoke().map { Resource.success(it) }
        emitSource(source)
    }

fun getRefFromString(stringRef: String, fileName: String? = null): StorageReference {

    try {
        if (fileName != null) {
            return Firebase.storage.getReferenceFromUrl(stringRef)
                .child(fileName)
        } else {
            return Firebase.storage.getReferenceFromUrl(stringRef)
        }
    } catch (ex: Exception) {
        Timber.e("--------------- Harshh getRefFromString Storage Exception : ${ex.localizedMessage}")
    }
    return Firebase.storage.getReferenceFromUrl(stringRef)
}