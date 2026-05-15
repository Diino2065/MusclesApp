package com.example.muscles.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "muscle_searches",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MuscleSearch(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val muscleName: String,
    val searchDate: Long = System.currentTimeMillis(),
    val timeSpentSeconds: Int = 0
)

