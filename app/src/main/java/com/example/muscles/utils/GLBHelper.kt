package com.example.muscles.utils

import android.content.Context
import java.io.File


object GLBHelper {

    fun checkGLBFile(context: Context): Boolean {
        return try {
            val assetManager = context.assets
            val inputStream = assetManager.open("human.glb")
            val size = inputStream.available()
            inputStream.close()

            size > 0
        } catch (e: Exception) {
            false
        }
    }

    fun getGLBFileSize(context: Context): Long {
        return try {
            val assetManager = context.assets
            val inputStream = assetManager.open("human.glb")
            val size = inputStream.available()
            inputStream.close()
            size.toLong()
        } catch (e: Exception) {
            0
        }
    }
}

