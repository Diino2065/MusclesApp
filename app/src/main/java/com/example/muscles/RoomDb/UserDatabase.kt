package com.example.muscles.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.muscles.RoomDb.UserDao
import com.example.muscles.RoomDb.Users
import com.example.muscles.RoomDb.MuscleSearch
import com.example.muscles.RoomDb.WaterIntake

@Database(entities = [Users::class, MuscleSearch::class, WaterIntake::class], version = 5)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
