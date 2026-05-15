package com.example.muscles.RoomDb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "water_intake",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WaterIntake(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val liters: Double,
    val date: String, // "yyyy-MM-dd" treba konvertovari u daljoj razradi
    val timestamp: Long = System.currentTimeMillis()
)

