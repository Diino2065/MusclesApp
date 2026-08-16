package com.example.muscles.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



fun futuristicBackgroundBrush(isDarkMode: Boolean): Brush {
    return if (isDarkMode) {
        Brush.radialGradient(
            colors = listOf(
                DarkPrimary.copy(alpha = 0.20f),
                DarkBackground,
                Color(0xFF040814)
            ),
            radius = 1600f
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                LightPrimary.copy(alpha = 0.06f),
                LightBackground,
                Color(0xFFF1F5FF)
            ),
            radius = 1600f
        )
    }
}

fun futuristicAccentBrush(accent: Color): Brush {
    return Brush.linearGradient(
        colors = listOf(
            accent.copy(alpha = 0.95f),
            accent.copy(alpha = 0.65f)
        )
    )
}

fun premiumGradientBrush(): Brush {
    return Brush.linearGradient(
        colors = listOf(
            GradientStart,
            GradientEnd,
            AccentGold
        )
    )
}

fun muscleGroupGradient(muscleColor: Color): Brush {
    return Brush.linearGradient(
        colors = listOf(
            muscleColor,
            muscleColor.copy(alpha = 0.6f)
        )
    )
}



@Composable
fun futuristicCardColors(isDarkMode: Boolean): CardColors {
    return CardDefaults.cardColors(
        containerColor = if (isDarkMode) DarkSurface.copy(alpha = 0.92f) else LightSurface
    )
}

@Composable
fun achievementCardColors(): CardColors {
    return CardDefaults.cardColors(
        containerColor = Color(0xFF1F2937).copy(alpha = 0.9f)
    )
}



@Composable
fun futuristicButtonColors(accent: Color): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = accent,
        contentColor = Color.White,
        disabledContainerColor = DisabledGray.copy(alpha = 0.5f),
        disabledContentColor = Color.White.copy(alpha = 0.5f)
    )
}

@Composable
fun primaryActionButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = DarkPrimary,
        contentColor = Color.White
    )
}

@Composable
fun secondaryActionButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = DarkSecondary,
        contentColor = Color.White
    )
}

@Composable
fun successButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = SuccessGreen,
        contentColor = Color.White
    )
}

@Composable
fun dangerButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = ErrorRed,
        contentColor = Color.White
    )
}



@Composable
fun futuristicTextFieldColors(isDarkMode: Boolean, accent: Color): androidx.compose.material3.TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = accent,
        unfocusedBorderColor = if (isDarkMode) Color(0xFF3B425D) else Color(0xFFD7DDEB),
        focusedTextColor = if (isDarkMode) DarkOnBackground else LightOnBackground,
        unfocusedTextColor = if (isDarkMode) DarkOnBackground else LightOnBackground,
        focusedLabelColor = accent,
        unfocusedLabelColor = if (isDarkMode) Color(0xFFB3C0E3) else Color(0xFF5D6472),
        cursorColor = accent
    )
}



val FuturisticSmallShape = RoundedCornerShape(8.dp)
val FuturisticMediumShape = RoundedCornerShape(12.dp)
val FuturisticLargeShape = RoundedCornerShape(16.dp)
val FuturisticExtraLargeShape = RoundedCornerShape(20.dp)



fun premiumCardElevation(isDarkMode: Boolean): Float {
    return if (isDarkMode) 8f else 4f
}

fun futuristicGlassEffect(isDarkMode: Boolean): Color {
    return if (isDarkMode) {
        Color.White.copy(alpha = 0.05f)
    } else {
        Color.Black.copy(alpha = 0.02f)
    }
}



const val ShortAnimationDuration = 200
const val MediumAnimationDuration = 400
const val LongAnimationDuration = 600

