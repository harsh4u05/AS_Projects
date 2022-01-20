package com.example.repositorymodule.entity.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.repositorymodule.entity.entities.Images

@Dao
interface ImageDao {

    @Query("SELECT * from images")
    fun getImages(): List<Images>

    @Query("SELECT * from images where imageName like :imageName")
    fun getImageByName(imageName: String): Images

    @Query("SELECT * from images where id like :imageId")
    fun getImageById(imageId: Int): Images

    @Insert
    fun insertImage(image: Images)

    @Insert
    fun insertImages(images: List<Images>)

    @Delete
    fun deleteImage(image: Images)

    @Delete
    fun deleteImages(images: List<Images>)

    @Query("DELETE from images where id like :imageId")
    fun deleteByImageId(imageId: Int)

    @Query("DELETE from images where imageName like :imageName")
    fun deleteByImageName(imageName: String)
}