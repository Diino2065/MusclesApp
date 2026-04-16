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
}