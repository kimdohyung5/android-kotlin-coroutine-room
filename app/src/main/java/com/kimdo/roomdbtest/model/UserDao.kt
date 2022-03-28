package com.kimdo.roomdbtest.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query( "Select * from user where username = :username")
    suspend fun getUser(username: String): User

    @Query("Delete from user where id = :id")
    suspend fun deleteUser(id: Long)
}