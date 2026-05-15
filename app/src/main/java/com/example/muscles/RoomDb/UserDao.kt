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
        profileImageUri = :profileImageUri WHERE username = :username
        """
    )
    suspend fun updateUserProfile(
        username: String,
        name: String,
        email: String,
        bio: String?,
        profileImageUri: String?
    ): Int

    @Insert
    suspend fun recordMuscleSearch(search: MuscleSearch)

    @Query("SELECT * FROM muscle_searches WHERE userId = :userId ORDER BY searchDate DESC")
    suspend fun getUserSearches(userId: Int): List<MuscleSearch>

    @Query(
        """
        SELECT muscleName, COUNT(*) AS searchCount
        FROM muscle_searches
        WHERE userId = :userId
        GROUP BY muscleName
        ORDER BY searchCount DESC, muscleName ASC
        LIMIT 5
        """
    )
    suspend fun getMostSearchedMuscles(userId: Int): List<MuscleSearchStats>

    @Query(
        """
        SELECT totalTimeSpentSeconds
        FROM users
        WHERE username = :username
        LIMIT 1
        """
    )
    suspend fun getTotalTimeSpentSeconds(username: String): Long?

    @Query(
        """
        UPDATE users SET totalTimeSpentSeconds = totalTimeSpentSeconds + :seconds,
        lastSessionStart = :newSessionStart WHERE id = :userId
        """
    )
    suspend fun updateSessionTime(userId: Int, seconds: Long, newSessionStart: Long): Int

    @Query("UPDATE users SET totalTimeSpentSeconds = :seconds WHERE username = :username")
    suspend fun updateTotalTime(username: String, seconds: Long)

     @Query(
         """
         UPDATE users SET lastSessionStart = :sessionStart WHERE id = :userId
         """
     )
     suspend fun updateSessionStart(userId: Int, sessionStart: Long): Int


     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun recordWaterIntake(waterIntake: WaterIntake)

     @Query(
         """
         SELECT * FROM water_intake
         WHERE userId = :userId AND date = :date
         LIMIT 1
         """
     )
     suspend fun getWaterIntakeForDate(userId: Int, date: String): WaterIntake?

     @Query(
         """
         SELECT * FROM water_intake
         WHERE userId = :userId
         ORDER BY date DESC
         LIMIT 30
         """
     )
     suspend fun getWaterIntakeHistory(userId: Int): List<WaterIntake>

     @Query(
         """
         UPDATE water_intake
         SET liters = :liters
         WHERE userId = :userId AND date = :date
         """
     )
     suspend fun updateWaterIntake(userId: Int, date: String, liters: Double): Int
}
