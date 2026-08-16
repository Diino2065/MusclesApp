package com.example.muscles.utils

import android.util.Patterns

object InputValidator {
    

    fun isValidEmail(email: String): Pair<Boolean, String> {
        return when {
            email.isEmpty() -> Pair(false, "Email cannot be empty")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> 
                Pair(false, "Please enter a valid email address")
            email.length > 254 -> Pair(false, "Email is too long (max 254 characters)")
            else -> Pair(true, "")
        }
    }
    

    fun isValidPassword(password: String): Pair<Boolean, String> {
        return when {
            password.isEmpty() -> Pair(false, "Password cannot be empty")
            password.length < 8 -> Pair(false, "Password must be at least 8 characters")
            !password.any { it.isUpperCase() } -> 
                Pair(false, "Password must contain at least one uppercase letter")
            !password.any { it.isLowerCase() } -> 
                Pair(false, "Password must contain at least one lowercase letter")
            !password.any { it.isDigit() } -> 
                Pair(false, "Password must contain at least one digit")
            !password.any { it in "!@#\$%^&*()" } -> 
                Pair(false, "Password must contain at least one special character (!@#\$%^&*)")
            else -> Pair(true, "")
        }
    }
    /**
     * Validate username
     * - 3-20 characters
     * - Only alphanumeric, underscore, and hyphen
     * - Must start with letter or number
     */
    fun isValidUsername(username: String): Pair<Boolean, String> {
        return when {
            username.isEmpty() -> Pair(false, "Username cannot be empty")
            username.length < 3 -> Pair(false, "Username must be at least 3 characters")
            username.length > 20 -> Pair(false, "Username must be at most 20 characters")
            !username[0].isLetterOrDigit() -> 
                Pair(false, "Username must start with a letter or number")
            !username.all { it.isLetterOrDigit() || it == '_' || it == '-' } -> 
                Pair(false, "Username can only contain letters, numbers, underscore, and hyphen")
            else -> Pair(true, "")
        }
    }
    

    fun isValidName(name: String): Pair<Boolean, String> {
        return when {
            name.isEmpty() -> Pair(false, "Name cannot be empty")
            name.length < 2 -> Pair(false, "Name must be at least 2 characters")
            name.length > 50 -> Pair(false, "Name must be at most 50 characters")
            !name.all { it.isLetter() || it.isWhitespace() || it == '-' || it == '\'' } -> 
                Pair(false, "Name can only contain letters, spaces, hyphens, and apostrophes")
            else -> Pair(true, "")
        }
    }
    

    fun isValidBio(bio: String): Pair<Boolean, String> {
        return when {
            bio.length > 500 -> Pair(false, "Bio must be at most 500 characters")
            else -> Pair(true, "")
        }
    }
    

    fun isValidHeight(height: Double): Pair<Boolean, String> {
        return when {
            height <= 0 -> Pair(false, "Height must be greater than 0")
            height < 50 || height > 300 -> Pair(false, "Height should be between 50-300 cm")
            else -> Pair(true, "")
        }
    }
    
    fun isValidWeight(weight: Double): Pair<Boolean, String> {
        return when {
            weight <= 0 -> Pair(false, "Weight must be greater than 0")
            weight < 20 || weight > 500 -> Pair(false, "Weight should be between 20-500 kg")
            else -> Pair(true, "")
        }
    }
    

    fun isValidWaterIntake(liters: Double): Pair<Boolean, String> {
        return when {
            liters < 0 -> Pair(false, "Water intake cannot be negative")
            liters > 20 -> Pair(false, "Water intake seems too high (max 20 liters)")
            else -> Pair(true, "")
        }
    }
}
