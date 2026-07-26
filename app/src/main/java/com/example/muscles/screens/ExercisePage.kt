package com.example.muscles.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import com.example.muscles.ui.theme.futuristicBackgroundBrush
import com.example.muscles.ui.theme.futuristicButtonColors
import com.example.muscles.ui.theme.futuristicCardColors

private data class WorkoutExercise(
    val name: String,
    val gif: String,
    val focus: String
)

private data class WorkoutPlan(
    val id: String,
    val title: String,
    val subtitle: String,
    val exercises: List<WorkoutExercise>,
    val color: Color
)

private val workoutPlans = listOf(
    WorkoutPlan(
        id = "push",
        title = "Push",
        subtitle = "Chest + Triceps",
        exercises = listOf(
            WorkoutExercise("Bench Press", "file:///android_asset/gifs/bench_press.gif", "Chest"),
            WorkoutExercise("Incline Dumbbell Press", "file:///android_asset/gifs/dumbbell_press.gif", "Chest"),
            WorkoutExercise("Tricep Dip", "file:///android_asset/gifs/tricep_dip.gif", "Triceps"),
            WorkoutExercise("Rope Pushdown", "file:///android_asset/gifs/rope_pushdown.gif", "Triceps"),
            WorkoutExercise("Overhead Extension", "file:///android_asset/gifs/overhead_extension.gif", "Triceps")
        ),
        color = Color(0xFFEF4444)
    ),
    WorkoutPlan(
        id = "pull",
        title = "Pull",
        subtitle = "Back + Biceps",
        exercises = listOf(
            WorkoutExercise("Pull-Up", "file:///android_asset/gifs/pullup.gif", "Back"),
            WorkoutExercise("Lat Pulldown", "file:///android_asset/gifs/lat_pulldown.gif", "Back"),
            WorkoutExercise("Bent Over Row", "file:///android_asset/gifs/bent_over_row.gif", "Back"),
            WorkoutExercise("Barbell Curl", "file:///android_asset/gifs/barbell_curl.gif", "Biceps"),
            WorkoutExercise("Hammer Curl", "file:///android_asset/gifs/hammer_curl.gif", "Biceps")
        ),
        color = Color(0xFF3B82F6)
    ),
    WorkoutPlan(
        id = "legs",
        title = "Legs",
        subtitle = "Quads + Hamstrings + Calves",
        exercises = listOf(
            WorkoutExercise("Back Squat", "file:///android_asset/gifs/back_squat.gif", "Quads"),
            WorkoutExercise("Leg Press", "file:///android_asset/gifs/leg_press.gif", "Quads"),
            WorkoutExercise("Leg Extension", "file:///android_asset/gifs/leg_extension.gif", "Quads"),
            WorkoutExercise("Leg Curl", "file:///android_asset/gifs/leg_curl.gif", "Hamstrings"),
            WorkoutExercise("Romanian Deadlift", "file:///android_asset/gifs/romanian_dl.gif", "Hamstrings"),
            WorkoutExercise("Standing Calf Raise", "file:///android_asset/gifs/standing_calf_raise.gif", "Calves")
        ),
        color = Color(0xFF22C55E)
    )
)

@Composable
fun ExercisePage(
    navController: NavController,
    username: String,
    isDarkMode: Boolean = true
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var selectedPlanId by rememberSaveable { mutableStateOf(workoutPlans.first().id) }
    var workoutRunning by rememberSaveable { mutableStateOf(false) }
    var workoutStartTime by rememberSaveable { mutableStateOf(0L) }
    var elapsedSeconds by rememberSaveable { mutableStateOf(0L) }
    var activeExerciseIndex by rememberSaveable { mutableStateOf(0) }

    val selectedPlan = workoutPlans.firstOrNull { it.id == selectedPlanId } ?: workoutPlans.first()
    val currentExercise = selectedPlan.exercises.getOrNull(activeExerciseIndex)
    val nextExercise = selectedPlan.exercises.getOrNull(activeExerciseIndex + 1)
    val currentProgress = if (selectedPlan.exercises.isEmpty()) 0f else (activeExerciseIndex + 1).toFloat() / selectedPlan.exercises.size.toFloat()

    LaunchedEffect(workoutRunning, workoutStartTime) {
        while (workoutRunning) {
            elapsedSeconds = ((System.currentTimeMillis() - workoutStartTime) / 1000).coerceAtLeast(0)
            delay(1000)
        }
    }

    fun startWorkout() {
        workoutStartTime = System.currentTimeMillis()
        elapsedSeconds = 0L
        activeExerciseIndex = 0
        workoutRunning = true
    }

    fun finishWorkout() {
        workoutRunning = false
    }

    fun nextExercise() {
        if (activeExerciseIndex < selectedPlan.exercises.lastIndex) {
            activeExerciseIndex += 1
        } else {
            workoutRunning = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(futuristicBackgroundBrush(isDarkMode))
            .padding(bottom = 24.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = if (isDarkMode) Color.White else Color.Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
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
                        text = { Text("Stats") },
                        onClick = {
                            menuExpanded = false
                            navController.navigate("Stats/$username")
                        }
                    )
                }

                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Muscles",
                        color = if (isDarkMode) Color.White else Color.Black,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic
                    )
                }

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Exit",
                    tint = if (isDarkMode) Color.White else Color.Black,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { navController.navigate("HomePage/$username") }
                )
            }
        }

        item {
            Text(
                text = "Workout",
                modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                color = if (isDarkMode) Color.White else Color.Black,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic
            )
        }

        item {
            Text(
                text = "Choose a split, press start workout, and track each exercise in order.",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                color = if (isDarkMode) Color(0xFFCBD5E1) else Color(0xFF475569),
                fontSize = 14.sp
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                workoutPlans.forEach { plan ->
                    val isSelected = plan.id == selectedPlanId
                    OutlinedButton(
                        onClick = {
                            if (!workoutRunning) {
                                selectedPlanId = plan.id
                                activeExerciseIndex = 0
                                elapsedSeconds = 0L
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !workoutRunning,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isSelected) plan.color else if (isDarkMode) Color.White else Color.Black
                        ),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text(plan.title)
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = futuristicCardColors(isDarkMode)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = selectedPlan.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = selectedPlan.color
                    )
                    Text(
                        text = selectedPlan.subtitle,
                        color = if (isDarkMode) Color(0xFFCBD5E1) else Color(0xFF475569),
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Workout time: ${formatSeconds(elapsedSeconds)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkMode) Color.White else Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { currentProgress },
                        modifier = Modifier.fillMaxWidth(),
                        color = selectedPlan.color,
                        trackColor = selectedPlan.color.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (workoutRunning) "Workout in progress" else "Press start workout to begin",
                        color = if (isDarkMode) Color(0xFFCBD5E1) else Color(0xFF475569),
                        fontSize = 13.sp
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { startWorkout() },
                    modifier = Modifier.weight(1f),
                    colors = futuristicButtonColors(selectedPlan.color),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Text("Start Workout", color = Color.White)
                }
                OutlinedButton(
                    onClick = { finishWorkout() },
                    modifier = Modifier.weight(1f),
                    enabled = workoutRunning,
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Text("Finish")
                }
            }
        }

        item {
            ExerciseStatusCard(
                title = "Current exercise",
                exercise = currentExercise,
                accent = selectedPlan.color,
                isDarkMode = isDarkMode,
                isHighlighted = true
            )
        }

        item {
            ExerciseStatusCard(
                title = "Next exercise",
                exercise = nextExercise,
                accent = Color(0xFF94A3B8),
                isDarkMode = isDarkMode,
                isHighlighted = false
            )
        }

        item {
            Button(
                onClick = { nextExercise() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                enabled = workoutRunning,
                colors = futuristicButtonColors(selectedPlan.color),
                shape = RoundedCornerShape(22.dp)
            ) {
                Text(
                    text = if (currentExercise == null || activeExerciseIndex == selectedPlan.exercises.lastIndex) {
                        "Complete Workout"
                    } else {
                        "Next Exercise"
                    },
                    color = Color.White
                )
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = futuristicCardColors(isDarkMode)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Workout checklist",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isDarkMode) Color.White else Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    selectedPlan.exercises.forEachIndexed { index, exercise ->
                        val status = when {
                            index < activeExerciseIndex -> "Done"
                            index == activeExerciseIndex && workoutRunning -> "Now"
                            else -> "Next"
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = exercise.name,
                                    color = if (isDarkMode) Color.White else Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = exercise.focus,
                                    color = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B),
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = status,
                                color = selectedPlan.color,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExerciseStatusCard(
    title: String,
    exercise: WorkoutExercise?,
    accent: Color,
    isDarkMode: Boolean,
    isHighlighted: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) Color(0xFF111827) else Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (isDarkMode) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (exercise == null) {
                Text(
                    text = if (isHighlighted) "Workout finished. Start again to repeat the split." else "No more exercises left.",
                    color = if (isDarkMode) Color(0xFFCBD5E1) else Color(0xFF475569),
                    fontSize = 13.sp
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.size(96.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = accent.copy(alpha = 0.14f))
                    ) {
                        AsyncImage(
                            model = exercise.gif,
                            contentDescription = exercise.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = exercise.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.White else Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = exercise.focus,
                            color = accent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Follow the movement, rest briefly, then tap next exercise.",
                            color = if (isDarkMode) Color(0xFFCBD5E1) else Color(0xFF475569),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

private fun formatSeconds(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}

@Preview(showBackground = true)
@Composable
fun ExercisePreview() {
    Surface {
        ExercisePage(navController = rememberNavController(), username = "previewUser")
    }
}
