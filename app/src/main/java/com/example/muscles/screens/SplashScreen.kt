package com.example.muscles.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    nextRoute: String
) {
    val navigated = remember { mutableStateOf(false) }
    val transition = rememberInfiniteTransition(label = "splashTransition")
    val leftRotation = transition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "leftArmRotation"
    )
    val rightRotation = transition.animateFloat(
        initialValue = 8f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rightArmRotation"
    )

    LaunchedEffect(Unit) {
        delay(2500)
        if (!navigated.value) {
            navigated.value = true
            navController.navigate(nextRoute) {
                popUpTo("Splash") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF12162E), Color(0xFF070A16)),
                    radius = 1200f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(280.dp, 280.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x2A62E0C8), Color.Transparent),
                        radius = 260f
                    ),
                    shape = RoundedCornerShape(999.dp)
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RealisticArm(
                        modifier = Modifier
                            .graphicsLayer { rotationZ = leftRotation.value }
                            .offset(y = 8.dp),
                        skin = Color(0xFFF1C7A7),
                        mirrored = false
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    RealisticArm(
                        modifier = Modifier
                            .graphicsLayer { rotationZ = rightRotation.value }
                            .offset(y = 8.dp),
                        skin = Color(0xFFE5B08D),
                        mirrored = true
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color.White.copy(alpha = 0.07f),
                shadowElevation = 10.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = "MUSCLES",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 38.sp,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Train smarter. See every muscle.",
                        color = Color(0xFFD6D8E8),
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Loading workout data...",
                color = Color(0xFFE4E4E4),
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(14.dp))
            CircularProgressIndicator(color = Color.White, strokeWidth = 3.dp)
        }
    }
}

@Composable
private fun RealisticArm(
    modifier: Modifier = Modifier,
    skin: Color,
    mirrored: Boolean
) {
    Box(
        modifier = modifier
            .graphicsLayer { scaleX = if (mirrored) -1f else 1f }
            .size(width = 128.dp, height = 172.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 22.dp, height = 22.dp)
                .offset(x = 0.dp, y = (-48).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(skin.copy(alpha = 0.98f), Color(0xFFB98269)),
                        radius = 32f
                    ),
                    shape = RoundedCornerShape(50)
                )
        )

        Box(
            modifier = Modifier
                .size(width = 84.dp, height = 112.dp)
                .offset(x = 5.dp, y = (-18).dp)
                .rotate(-6f)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFFF5D1B5),
                            skin,
                            Color(0xFF9E6A55)
                        )
                    ),
                    RoundedCornerShape(42.dp)
                )
        )

        Box(
            modifier = Modifier
                .size(width = 46.dp, height = 92.dp)
                .offset(x = 27.dp, y = 44.dp)
                .rotate(9f)
                .background(
                    Brush.linearGradient(
                        listOf(
                            skin.copy(alpha = 0.98f),
                            Color(0xFFC88C6E),
                            Color(0xFF8E5D49)
                        )
                    ),
                    RoundedCornerShape(24.dp)
                )
        )

        Box(
            modifier = Modifier
                .size(width = 48.dp, height = 30.dp)
                .offset(x = 30.dp, y = 110.dp)
                .rotate(8f)
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFFF6CEB0), Color(0xFFD4A084))
                    ),
                    RoundedCornerShape(18.dp)
                )
        )

        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 150.dp)
                .background(
                    Brush.linearGradient(
                        listOf(Color.Transparent, Color(0x22000000))
                    ),
                    RoundedCornerShape(48.dp)
                )
        )

        Box(
            modifier = Modifier
                .size(width = 14.dp, height = 14.dp)
                .offset(x = 36.dp, y = 118.dp)
                .background(Color(0xFFE7C0A4), RoundedCornerShape(8.dp))
        )
    }
}
@Preview
@Composable
fun SplashScreenPreview() {
        SplashScreen(
            navController = rememberNavController(),
            nextRoute = "Home"
        )
}