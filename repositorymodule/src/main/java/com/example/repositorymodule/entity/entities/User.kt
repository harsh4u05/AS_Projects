package com.example.repositorymodule.entity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @ColumnInfo(name = "uid")  @PrimaryKey val uid: Int,
    @ColumnInfo(name = "userName") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name="Email") val email: String
        )