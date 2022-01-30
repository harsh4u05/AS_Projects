package com.example.repositorymodule.entity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.internal.StorageReferenceUri

@Entity
data class Images (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val imageId: Int = 0,
    @ColumnInfo(name = "imageName") val imageUri: String
   // @ColumnInfo(name = "imageRef") val imageRef: StorageReference
    )