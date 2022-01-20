package com.example.repositorymodule.entity.firestorage

import androidx.lifecycle.LiveData
import com.example.repositorymodule.utils.Resource

//class to return LiveData as Resource<T>
abstract class BaseStorageDataSource {

    fun <T> getResult(call: () -> LiveData<T>): Resource<T> {
        val result = call()
        if (result.value != null) {
            return Resource.success(result.value!!)
        }
        return Resource.error("Error in Get image.")
    }
}