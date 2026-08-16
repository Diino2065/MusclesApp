package com.example.muscles.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muscles.ui.theme.DarkBackground
import com.example.muscles.ui.theme.DarkOnBackground
import com.example.muscles.ui.theme.DarkSurface
import com.example.muscles.ui.theme.ErrorRed
import com.example.muscles.ui.theme.FuturisticMediumShape
import com.example.muscles.ui.theme.SuccessGreen
import com.example.muscles.ui.theme.WarningOrange



@Composable
fun LoadingScreen(message: String = "Loading...") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = Color(0xFF6366F1),
                strokeWidth = 4.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                color = DarkOnBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun LoadingCard(isLoading: Boolean = false) {
    if (isLoading) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = FuturisticMediumShape
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = Color(0xFF6366F1),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}



@Composable
fun EmptyStateScreen(
    icon: String = "📭",
    title: String = "No Data",
    message: String = "There's nothing to show here yet",
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = icon,
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                color = DarkOnBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = Color(0xFFB3C0E3),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            if (actionText != null && onAction != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onAction) {
                    Text(actionText)
                }
            }
        }
    }
}



@Composable
fun SuccessCard(title: String, message: String) {
    StateCard(
        icon = Icons.Default.CheckCircle,
        iconColor = SuccessGreen,
        title = title,
        message = message,
        backgroundColor = SuccessGreen.copy(alpha = 0.1f)
    )
}

@Composable
fun ErrorCard(title: String, message: String) {
    StateCard(
        icon = Icons.Default.Error,
        iconColor = ErrorRed,
        title = title,
        message = message,
        backgroundColor = ErrorRed.copy(alpha = 0.1f)
    )
}

@Composable
fun WarningCard(title: String, message: String) {
    StateCard(
        icon = Icons.Default.Warning,
        iconColor = WarningOrange,
        title = title,
        message = message,
        backgroundColor = WarningOrange.copy(alpha = 0.1f)
    )
}

@Composable
fun InfoCard(title: String, message: String) {
    StateCard(
        icon = Icons.Default.Info,
        iconColor = Color(0xFF3B82F6),
        title = title,
        message = message,
        backgroundColor = Color(0xFF3B82F6).copy(alpha = 0.1f)
    )
}

@Composable
fun StateCard(
    icon: ImageVector? = null,
    iconColor: Color = Color.Gray,
    title: String,
    message: String,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = FuturisticMediumShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = DarkOnBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    color = DarkOnBackground.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Composable
fun AchievementBadge(
    icon: String,
    title: String,
    subtitle: String = "",
    points: Int = 0,
    isUnlocked: Boolean = true
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) Color(0xFF4B5563) else DarkSurface.copy(alpha = 0.5f)
        ),
        shape = FuturisticMediumShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        if (isUnlocked) Color(0xFF6366F1).copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 32.sp)
            }
            Spacer(modifier = Modifier.padding(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = DarkOnBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        color = Color(0xFFB3C0E3),
                        fontSize = 12.sp
                    )
                }
            }
            if (points > 0) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFB700), shape = CircleShape)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+$points",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}



@Composable
fun ProgressBar(
    progress: Float,
    label: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                color = DarkOnBackground,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFF3B425D), shape = FuturisticMediumShape),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .background(
                        Color(0xFF6366F1),
                        shape = FuturisticMediumShape
                    )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${(progress * 100).toInt()}%",
            color = Color(0xFFB3C0E3),
            fontSize = 11.sp,
            modifier = Modifier.align(Alignment.End)
        )
    }
}
