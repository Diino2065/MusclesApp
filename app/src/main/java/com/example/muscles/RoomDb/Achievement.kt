package com.example.muscles.RoomDb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Achievement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val achievementType: String,
    val title: String,
    val description: String,
    val icon: String,  // emoji or drawable reference
    val pointsReward: Int,
    val unlockedDate: Long = System.currentTimeMillis(),
    val isUnlocked: Boolean = true
)

@Entity(
    tableName = "user_streaks",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserStreak(
    @PrimaryKey val userId: Int,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActivityDate: Long = 0,
    val totalDaysActive: Int = 0
)

@Entity(
    tableName = "workout_sessions",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val date: Long = System.currentTimeMillis(),
    val durationSeconds: Long = 0,
    val exerciseCount: Int = 0,
    val totalSets: Int = 0,
    val totalReps: Int = 0,
    val pointsEarned: Int = 0,
    val notes: String? = null
)

@Entity(
    tableName = "exercise_logs",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSession::class,
            parentColumns = ["id"],
            childColumns = ["workoutSessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val workoutSessionId: Int,
    val exerciseName: String,
    val muscleGroup: String,
    val sets: Int,
    val reps: Int,
    val weight: Double = 0.0,
    val restTimeSeconds: Int = 0,
    val notes: String? = null,
    val difficulty: String? = null  // "Easy", "Medium", "Hard"
)

@Entity(
    tableName = "daily_challenges",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DailyChallenge(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val date: Long,
    val challengeName: String,
    val description: String,
    val targetCount: Int,
    val currentProgress: Int = 0,
    val pointsReward: Int = 50,
    val isCompleted: Boolean = false,
    val completedDate: Long? = null
)

@Entity(
    tableName = "user_badges",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserBadge(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val badgeType: String,
    val badgeName: String,
    val icon: String,
    val earnedDate: Long = System.currentTimeMillis()
)
