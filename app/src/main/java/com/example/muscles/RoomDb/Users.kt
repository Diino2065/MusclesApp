package com.example.muscles.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val username: String,
    val password: String,  // Now stores hashed password
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
    
    // Profile Enhancement
    val age: Int? = null,
    val gender: String? = null,  // "M", "F", "Other"
    val experienceLevel: String? = null,  // "Beginner", "Intermediate", "Advanced"
    val fitnessGoal: String? = null,  // "Strength", "Endurance", "Flexibility", "Weight Loss", "Muscle Gain"
    val height: Double? = null,  // in cm
    val weight: Double? = null,  // in kg
    val isEmailVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis()
)


