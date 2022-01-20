package com.example.repositorymodule.entity.local

import androidx.room.*
import com.example.repositorymodule.entity.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

//    @Query("SELECT * FROM user WHERE userIds IN (:userIds)")
//    fun selectAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE username like :username")
    fun findByName(username: String): User

    @Query("SELECT * FROM user WHERE email like :email")
    fun findByEmail(email: String): User

    @Query("SELECT * FROM user WHERE uid like :uid")
    fun findUserById(uid: Int): User

    @Insert
    suspend fun insertAll(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}