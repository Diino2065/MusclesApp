package com.example.muscles.RoomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.*


@Dao
interface UserDao {

    @Insert suspend fun register(user: Users)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): Users?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): Users?

    @Query(
        """
 UPDATE users SET name = :name,
 email = :email,
 bio = :bio,
 profileImageUri = :profileImageUri WHERE username = :username """
    )
    suspend fun updateUserProfile(
        username: String,
        name: String,
        email: String,
        bio: String?,
        profileImageUri: String?
    ): Int
}



