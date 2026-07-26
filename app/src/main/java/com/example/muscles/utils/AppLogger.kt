package com.example.muscles.utils

import android.util.Log

object AppLogger {
    private const val TAG = "MusclesApp"
    
    fun logAction(action: String, details: String = "") {
        val message = if (details.isNotEmpty()) "[$action] $details" else "[$action]"
        Log.i(TAG, message)
    }
    
    fun logError(error: String, exception: Exception? = null) {
        if (exception != null) {
            Log.e(TAG, "[ERROR] $error", exception)
        } else {
            Log.e(TAG, "[ERROR] $error")
        }
    }
    
    fun logWarning(warning: String) {
        Log.w(TAG, "[WARNING] $warning")
    }
    
    fun logInfo(info: String) {
        Log.d(TAG, "[INFO] $info")
    }
    
    fun logDebug(debug: String) {
        Log.d(TAG, "[DEBUG] $debug")
    }
    
    fun logUserAction(userId: Int, action: String) {
        logAction("USER_ACTION", "UserID: $userId, Action: $action")
    }
    
    fun logDatabaseOperation(operation: String, status: String, details: String = "") {
        val msg = "Operation: $operation, Status: $status" + if (details.isNotEmpty()) ", Details: $details" else ""
        logInfo(msg)
    }
    
    fun logPerformance(operation: String, durationMs: Long) {
        logInfo("$operation completed in ${durationMs}ms")
    }
}
