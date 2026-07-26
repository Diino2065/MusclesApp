package com.example.muscles.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.White,
    secondary = DarkSecondary,
    onSecondary = Color.Black,
    tertiary = DarkTertiary,
    onTertiary = Color.Black,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    error = DarkErrorRed,
    onError = Color.Black,
    surfaceVariant = Color(0xFF2D3142),
    outlineVariant = Color(0xFF49454E)
)

private val LightColorScheme = lightColorScheme(
    primary = DarkPrimary,  // Keep vibrant primary even in light mode
    onPrimary = Color.White,
    secondary = DarkSecondary,
    onSecondary = Color.White,
    tertiary = Color(0xFF5A4A8A),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    error = LightErrorRed,
    onError = Color.White,
    surfaceVariant = Color(0xFFF5F5F5),
    outlineVariant = Color(0xFFCAC4D0)
)

@Composable
fun MusclesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}