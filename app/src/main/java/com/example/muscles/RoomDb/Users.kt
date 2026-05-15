package com.example.muscles.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val bio: String?= null,
    val profileImageUri: String?,
    val totalTimeSpentSeconds: Long = 0,
    val lastSessionStart: Long = 0
) {


}

