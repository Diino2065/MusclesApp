package com.example.muscles.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muscles.RoomDb.UserViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.muscles.ui.theme.DarkBackground
import com.example.muscles.ui.theme.DarkOnBackground
import com.example.muscles.ui.theme.DarkPrimary
import com.example.muscles.ui.theme.DarkSurface
import com.example.muscles.ui.theme.LightBackground
import com.example.muscles.ui.theme.LightOnBackground
import com.example.muscles.ui.theme.LightPrimary
import com.example.muscles.ui.theme.LightSurface
import com.example.muscles.ui.theme.futuristicBackgroundBrush
import com.example.muscles.ui.theme.futuristicButtonColors
import com.example.muscles.ui.theme.futuristicCardColors
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import com.example.muscles.RoomDb.WaterIntake



@Composable
fun Stats(
    navController: NavController,
    username: String,
    userViewModel: UserViewModel = viewModel(),
    isDarkMode: Boolean = true
) {
    var totalTime by remember { mutableStateOf("00:00:00") }
    var totalSeconds by remember { mutableStateOf(0L) }
    var mostSearchedMuscles by remember { mutableStateOf<List<Pair<String, Int>>>(emptyList()) }
    var waterIntakeHistory by remember { mutableStateOf<List<WaterIntake>>(emptyList()) }

    fun parseHmsToSeconds(hms: String): Long {
        val parts = hms.split(":")
        if (parts.size != 3) return 0L

        val h = parts[0].toLongOrNull() ?: 0L
        val m = parts[1].toLongOrNull() ?: 0L
        val s = parts[2].toLongOrNull() ?: 0L

        return h * 3600 + m * 60 + s
    }
    fun formatSecondsToHms(seconds: Long): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60

        return String.format("%02d:%02d:%02d", h, m, s)
    }

    LaunchedEffect(username) {
        if (username.isBlank()) return@LaunchedEffect

        userViewModel.getTotalTimeSeconds(username) { seconds ->
            totalSeconds = seconds
            totalTime = formatSecondsToHms(seconds)
        }

        userViewModel.getUserByUsername(username) { user ->
            if (user != null) {
                userViewModel.getMostSearchedMuscles(user.id) { muscles ->
                    mostSearchedMuscles = muscles
                }
                userViewModel.getWaterIntakeHistory(user.id) { history ->
                    waterIntakeHistory = history
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)

            totalSeconds += 1
            totalTime = formatSecondsToHms(totalSeconds)

            userViewModel.updateTotalTime(username, totalSeconds)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(futuristicBackgroundBrush(isDarkMode)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier .fillMaxWidth()
                .padding(top =24.dp, bottom =12.dp)
        ) {
            var menuExpanded by remember { mutableStateOf(false) }

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = if (isDarkMode) DarkOnBackground else LightOnBackground,
                modifier = Modifier .padding(start =16.dp)
                    .clickable { menuExpanded = true }
            )

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Home Page") },
                    onClick = {
                        menuExpanded = false
                        navController.navigate("HomePage/$username")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Profile Page") },
                    onClick = {
                        menuExpanded = false
                        navController.navigate("ProfilePage/$username")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Exercise") },
                    onClick = {
                        menuExpanded = false
                        navController.navigate("Exercise/$username")
                    }
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center ) {
                Text(
                    text = "Muscles",
                    color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                    fontSize =32.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    letterSpacing =0.5.sp )
            }

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Exit",
                tint = if (isDarkMode) DarkOnBackground else LightOnBackground,
                modifier = Modifier .padding(end =16.dp)
                    .clickable { navController.navigate("ProfilePage/$username") }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Stats",
                    modifier = Modifier.padding(start =30.dp, top =20.dp),
                    color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                    fontSize =30.sp,
                    fontStyle = FontStyle.Italic )
            }
            item {
                StatsCard(totalTime, isDarkMode)
            }
            item {
                MostSearchedMuscles(mostSearchedMuscles, isDarkMode)
            }
            item {
                WaterIntakeStats(waterIntakeHistory, isDarkMode)
            }
        }
    }
}

@Composable
fun StatsCard(totalTime: String, isDarkMode: Boolean = true) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = futuristicCardColors(isDarkMode),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Total Time Spent",
                fontSize =16.sp,
                color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                fontWeight = FontWeight.Bold )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = totalTime,
                fontSize =32.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkMode) DarkPrimary else LightPrimary )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "This shows the total time stored for this user.",
                fontSize =14.sp,
                color = if (isDarkMode) Color.Gray else Color(0xFF666666)
            )
        }
    }
}

@Composable
fun MostSearchedMuscles(muscles: List<Pair<String, Int>>, isDarkMode: Boolean = true) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = futuristicCardColors(isDarkMode)
    ) {
        Column(modifier = Modifier.padding(bottom =12.dp)) {
            Text(
                text = "Most Searched Muscles",
                modifier = Modifier.padding(top =20.dp, start =20.dp),
                color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                fontSize =16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic )

            if (muscles.isEmpty()) {
                Text(
                    text = "No searches yet",
                    modifier = Modifier.padding(20.dp),
                    color = if (isDarkMode) Color.Gray else Color(0xFF999999),
                    fontSize =14.sp )
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    muscles.forEach { (muscleName, count) ->
                        Row(
                            modifier = Modifier .fillMaxWidth()
                                .padding(horizontal =16.dp, vertical =10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically ) {
                            Text(
                                text = muscleName,
                                color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                                fontSize =14.sp )
                            Card(
                                modifier = Modifier.padding(start =10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkMode) DarkPrimary else LightPrimary ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "$count searches",
                                    modifier = Modifier.padding(horizontal =12.dp, vertical =4.dp),
                                    color = Color.White,
                                    fontSize =12.sp )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WaterIntakeStats(waterIntakeHistory: List<WaterIntake>, isDarkMode: Boolean = true) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = futuristicCardColors(isDarkMode)
    ) {
        Column(modifier = Modifier.padding(bottom = 12.dp)) {
            Text(
                text = "💧 Daily Water Intake",
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic )

            if (waterIntakeHistory.isEmpty()) {
                Text(
                    text = "No water intake recorded yet",
                    modifier = Modifier.padding(20.dp),
                    color = if (isDarkMode) Color.Gray else Color(0xFF999999),
                    fontSize = 14.sp )
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    waterIntakeHistory.forEach { water ->
                        Row(
                            modifier = Modifier .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically ) {
                            Column {
                                Text(
                                    text = water.date,
                                    color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold )
                            }
                            Card(
                                modifier = Modifier.padding(start = 10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkMode) DarkPrimary else LightPrimary ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "${water.liters} L",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    color = Color.White,
                                    fontSize = 12.sp )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    Stats(navController = rememberNavController(), username = "previewUser", userViewModel = viewModel())
}
