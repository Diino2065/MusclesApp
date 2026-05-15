package com.example.muscles.RoomDb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscles.data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = UserDatabase.getDatabase(application).userDao()

    fun registerUser(user: Users, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                userDao.register(user)
                withContext(Dispatchers.Main) { onResult(true) }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(false) }
            }
        }
    }

    fun loginUser(username: String, password: String, onResult: (Users?) -> Unit) {
        viewModelScope.launch {
            val user = userDao.login(username, password)
            withContext(Dispatchers.Main) { onResult(user) }
        }
    }

    fun getUserByUsername(username: String, onResult: (Users?) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserByUsername(username)
            withContext(Dispatchers.Main) { onResult(user) }
        }
    }

    fun updateUserProfile(
        username: String,
        name: String,
        email: String,
        bio: String?,
        profileImageUri: String?,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val rowsUpdated = userDao.updateUserProfile(username, name, email, bio, profileImageUri)
            withContext(Dispatchers.Main) { onResult(rowsUpdated > 0) }
        }
    }

    fun recordMuscleSearch(userId: Int, muscleName: String) {
        viewModelScope.launch {
            try {
                userDao.recordMuscleSearch(
                    MuscleSearch(
                        userId = userId,
                        muscleName = muscleName,
                        searchDate = System.currentTimeMillis()
                    )
                )
            } catch (_: Exception) {
            }
        }
    }

    fun getMostSearchedMuscles(userId: Int, onResult: (List<Pair<String, Int>>) -> Unit) {
        viewModelScope.launch {
            try {
                val results = userDao.getMostSearchedMuscles(userId)
                withContext(Dispatchers.Main) {
                    onResult(results.map { it.muscleName to it.searchCount })
                }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult(emptyList()) }
            }
        }
    }

    fun updateSessionTime(userId: Int, seconds: Long, newSessionStart: Long = System.currentTimeMillis()) {
        viewModelScope.launch {
            try {
                userDao.updateSessionTime(userId, seconds, newSessionStart)
            } catch (_: Exception) {
            }
        }
    }

    fun startUserSession(userId: Int) {
        viewModelScope.launch {
            try {
                userDao.updateSessionStart(userId, System.currentTimeMillis())
            } catch (_: Exception) {
            }
        }
    }
    fun updateTotalTime(username: String, seconds: Long) {
        viewModelScope.launch {
            userDao.updateTotalTime(username, seconds)
        }
    }
/*
    fun getTotalTimeSpentSecodns(username: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val totalSeconds = userDao.getTotalTimeSpentSeconds(username) ?: 0L
                val hours = totalSeconds / 3600
                val minutes = (totalSeconds % 3600) / 60
                val secs = totalSeconds % 60
                withContext(Dispatchers.Main) {
                    onResult(String.format("%02d:%02d:%02d", hours, minutes, secs))
                }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) { onResult("00:00:00") }
            }
        }
    }*/
    fun recordWaterIntake(userId: Int, liters: Double, date: String) {
        viewModelScope.launch {
            try {
                val waterIntake = WaterIntake(
                    userId = userId,
                    liters = liters,
                    date = date
                )
                userDao.recordWaterIntake(waterIntake)
            } catch (_: Exception) {
            }
        }
    }

    fun getWaterIntakeForDate(userId: Int, date: String, onResult: (Double) -> Unit) {
        viewModelScope.launch {
            try {
                val waterIntake = userDao.getWaterIntakeForDate(userId, date)
                val liters = waterIntake?.liters ?: 0.0
                withContext(Dispatchers.Main) {
                    onResult(liters)
                }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(0.0)
                }
            }
        }
    }

    fun getWaterIntakeHistory(userId: Int, onResult: (List<WaterIntake>) -> Unit) {
        viewModelScope.launch {
            try {
                val history = userDao.getWaterIntakeHistory(userId)
                withContext(Dispatchers.Main) {
                    onResult(history)
                }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(emptyList())
                }
            }
        }
    }

    fun updateWaterIntake(userId: Int, date: String, liters: Double) {
        viewModelScope.launch {
            try {
                userDao.updateWaterIntake(userId, date, liters)
            } catch (_: Exception) {
            }
        }
    }

fun getTotalTimeSeconds(username: String, onResult: (Long) -> Unit) {
    viewModelScope.launch {
        val seconds = userDao.getTotalTimeSpentSeconds(username) ?: 0L
        withContext(Dispatchers.Main) {
            onResult(seconds)
        }
    }
}
}