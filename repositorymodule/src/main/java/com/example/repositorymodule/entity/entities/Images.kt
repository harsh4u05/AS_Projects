package com.example.repositorymodule.entity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Images (
    @ColumnInfo(name = "id") @PrimaryKey val imageId: Int,
    @ColumnInfo(name = "imageName") val imageName: String
    )