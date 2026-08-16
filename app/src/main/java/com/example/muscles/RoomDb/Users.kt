package com.example.muscles.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val username: String,
    val password: String,  //hash
    val bio: String?= null,
    val profileImageUri: String?,
    val totalTimeSpentSeconds: Long = 0,
    val lastSessionStart: Long = 0,
    
    // Gamification Fields
    val totalPoints: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActiveDate: Long = 0,
    val totalWorkouts: Int = 0,
    val totalExercisesCompleted: Int = 0,
    

    val age: Int? = null,
    val gender: String? = null,
    val experienceLevel: String? = null,
    val fitnessGoal: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val isEmailVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis()
)


