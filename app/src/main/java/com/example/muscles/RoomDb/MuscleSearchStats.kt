package com.example.muscles.RoomDb

import androidx.room.ColumnInfo

data class MuscleSearchStats(
    @ColumnInfo(name = "muscleName")
    val muscleName: String,
    @ColumnInfo(name = "searchCount")
    val searchCount: Int
)

