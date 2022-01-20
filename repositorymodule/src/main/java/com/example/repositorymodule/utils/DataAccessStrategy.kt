package com.example.repositorymodule.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <T> performGetOperation(
    storageQuery: () -> LiveData<T>
    ): LiveData<Resource<T>> =

    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = storageQuery.invoke().map { Resource.success(it) }
        emitSource(source)
    }

