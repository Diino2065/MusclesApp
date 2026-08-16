package com.example.muscles.screens

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muscles.RoomDb.UserViewModel
import com.example.muscles.RoomDb.Users
import java.util.Date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.rotate
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.SubcomposeAsyncImage
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import android.annotation.SuppressLint
import android.util.Log
import com.example.muscles.R
import com.example.muscles.ui.theme.DarkSurface
import com.example.muscles.ui.theme.futuristicBackgroundBrush
import com.example.muscles.ui.theme.futuristicButtonColors
import com.example.muscles.ui.theme.futuristicCardColors
import com.example.muscles.ui.theme.futuristicTextFieldColors
import com.example.muscles.ui.theme.ShortAnimationDuration
import com.example.muscles.ui.theme.MediumAnimationDuration
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import java.util.Locale
import java.text.SimpleDateFormat

// prikazuje se jedna radnom u notification ikoni u daljoj razradi dodati i pravi chatbot za app challange
private val chatbotResponses = listOf(
    "💧 Pij najmanje 2-3 litre vode dnevno za bolji metabolism!",
    "🥗 Konzumiraj više proteina nakon treninga za bolje mišićne rezultate!",
    "🏃 Redovna vježbanja 30 minuta dnevno čini razliku u zdravlju!",
    "😴 7-9 sati sna je ključno za oporavak mišića!",
    "🥤 Izbjegavaj preslana i slatka pića - voda je najbolja!",
    "🍎 Jedis voće i povrće za dostatne vitamine i minerale!",
    "💪 Kontinuitetno treniranje je ključne - ne odustaj!",
    "🥤 Pij vodu prije, tijekom i nakon treninga!",
    "🎯 Postavi realne ciljeve i prati napredak!",
    "📊 Praćenje ishrane pomaže u postizanju boljih rezultata!"
)



data class Exercise(
    val name: String,
    val gif: String
)

data class MuscleInfo(
    val id: String,
    val name: String,
    val function: String,
    val exercises: List<Exercise>,
    val primaryMovement: String,
    val secondaryMuscles: String,
    val trainingTip: String,
    val difficulty: String
)

private val muscleDatabase = listOf(
    MuscleInfo(
        "triceps",
        "Triceps Brachii",
        "The triceps brachii is the large muscle on the back of the upper arm responsible for elbow extension. It contributes significantly to pressing strength and arm stability during pushing exercises.",
        listOf(
            Exercise("Tricep Dip", "file:///android_asset/gifs/tricep_dip.gif"),
            Exercise("Rope Pushdown", "file:///android_asset/gifs/rope_pushdown.gif"),
            Exercise("Overhead Extension", "file:///android_asset/gifs/overhead_extension.gif")
        ),
        "Elbow extension",
        "Anterior deltoid, chest during pressing",
        "Train from multiple angles to fully activate all three tricep heads.",
        "Beginner to advanced"
    ),

    MuscleInfo(
        "back",
        "Latissimus Dorsi",
        "The latissimus dorsi is a large back muscle responsible for pulling movements and shoulder extension. Well-developed lats contribute to posture, upper-body strength, and the classic V-taper physique.",
        listOf(
            Exercise("Pull-Up", "file:///android_asset/gifs/pullup.gif"),
            Exercise("Lat Pulldown", "file:///android_asset/gifs/lat_pulldown.gif"),
            Exercise("Bent Over Row", "file:///android_asset/gifs/bent_over_row.gif")
        ),
        "Pulling the elbows down and backward",
        "Rhomboids, traps, rear delts, biceps",
        "Focus on driving the elbows rather than pulling with the hands.",
        "Intermediate"
    ),

    MuscleInfo(
        "abs",
        "Rectus Abdominis",
        "The rectus abdominis forms the front abdominal wall and is responsible for trunk flexion, posture support, and core stabilization. Strong abs improve balance, athletic performance, and spinal protection.",
        listOf(
            Exercise("Crunches", "file:///android_asset/gifs/crunches.gif"),
            Exercise("Planks", "file:///android_asset/gifs/plank.gif"),
            Exercise("Ab Wheel", "file:///android_asset/gifs/ab_wheel.gif")
        ),
        "Core bracing and trunk flexion",
        "Obliques, transverse abdominis, hip flexors",
        "Prioritize controlled breathing and core tension during abdominal training.",
        "Beginner to advanced"
    ),

    MuscleInfo(
        "quads",
        "Quadriceps",
        "The quadriceps are a powerful group of thigh muscles responsible for knee extension, jumping, sprinting, and lower-body strength. They are heavily involved in squats, lunges, and athletic movement.",
        listOf(
            Exercise("Back Squat", "file:///android_asset/gifs/back_squat.gif"),
            Exercise("Leg Press", "file:///android_asset/gifs/leg_press.gif"),
            Exercise("Leg Extension", "file:///android_asset/gifs/leg_extension.gif")
        ),
        "Knee extension and explosive leg drive",
        "Glutes, hamstrings, calves",
        "Keep knees stable and control the eccentric phase during leg exercises.",
        "Beginner to advanced"
    ),
    MuscleInfo(
        "chest",
        "Pectoralis Major",
        "The pectoralis major is a large upper-body muscle responsible for pushing strength, horizontal arm movement, and shoulder stabilization. It is heavily activated during pressing exercises and contributes significantly to chest development and upper-body power.",
        listOf(
            Exercise("Bench Press", "file:///android_asset/gifs/bench_press.gif"),
            Exercise("Push Up", "file:///android_asset/gifs/pushup.gif"),
            Exercise("Incline Dumbbell Press", "file:///android_asset/gifs/dumbbell_press.gif")
        ),
        "Horizontal pressing and arm adduction",
        "Front deltoids, triceps, serratus anterior",
        "Keep shoulder blades retracted and control the lowering phase for better chest activation.",
        "Beginner to advanced"
    ),

    MuscleInfo(
        "biceps",
        "Biceps Brachii",
        "The biceps brachii is a major muscle located on the front side of the upper arm. Its primary functions are elbow flexion and forearm supination, making it essential for pulling movements, lifting, and upper-body strength. Well-developed biceps improve arm aesthetics, grip support, and performance in exercises such as rows, pull-ups, and curls.",
        listOf(
            Exercise(
                "Dumbbell Curl",
                "file:///android_asset/gifs/dumbbell_curl.gif"),
            Exercise(
                "Barbell Curl",
                "file:///android_asset/gifs/barbell_curl.gif"),
            Exercise(
                "Hammer Curl",
                "file:///android_asset/gifs/hammer_curl.gif")
        ),
        "Elbow flexion and forearm supination",
        "Brachialis, brachioradialis, forearms",
        "Use controlled repetitions and avoid swinging your body during curls to maximize muscle activation.",
        "Beginner to advanced"
    ),

    MuscleInfo(
        "calves",
        "Gastrocnemius",
        "The gastrocnemius and soleus muscles form the calf complex and are essential for walking, sprinting, jumping, and ankle stability. Strong calves improve athletic performance and lower-leg endurance.",
        listOf(
            Exercise("Standing Calf Raise", "file:///android_asset/gifs/standing_calf_raise.gif"),
            Exercise("Seated Calf Raise", "file:///android_asset/gifs/seated_calf_raise.gif"),
            Exercise("Jump Rope", "file:///android_asset/gifs/jump_rope.gif")
        ),
        "Ankle extension and lower-leg stabilization",
        "Tibialis anterior, peroneals, foot stabilizers",
        "Pause at the top and fully stretch the calves at the bottom of each repetition.",
        "Beginner to advanced"
    ),

    MuscleInfo(
        "glutes",
        "Gluteus Maximus",
        "The gluteus maximus is the largest and strongest muscle in the human body. It is responsible for hip extension, pelvic stability, posture, sprinting power, and explosive lower-body movement. Strong glutes improve athletic performance, lower-body strength, and reduce stress on the lower back and knees.",
        listOf(
            Exercise(
                "Romanian Deadlift",
                "file:///android_asset/gifs/romanian_dl.gif"
            ),
            Exercise(
                "Single Leg Pull Through",
                "file:///android_asset/gifs/snigle_leg.gif"
            ),
            Exercise(
                "Bulgarian Split Squat",
                "file:///android_asset/gifs/bulgarian_split_squat.gif"
            )
        ),
        "Hip extension and pelvic stabilization",
        "Hamstrings, lower back, quadriceps",
        "Focus on squeezing the glutes at the top of each repetition and maintaining full hip control.",
        "Beginner to advanced"
    ),

    MuscleInfo(
        "hamstrings",
        "Hamstrings",
        "The hamstrings are a group of muscles located on the back of the thigh responsible for knee flexion and hip extension. They are heavily involved in running, sprinting, jumping, and stabilizing the lower body during athletic movement.",
        listOf(
            Exercise(
                "Leg Curl",
                "file:///android_asset/gifs/leg_curl.gif"
            ),
            Exercise(
                "Romanian Deadlift",
                "file:///android_asset/gif/romanian_dl.gif"
            ),
            Exercise(
                "Decilne leg curl",
                "file:///android_asset/gifs/decline_lg.gif"
            )
        ),
        "Knee flexion and hip extension",
        "Glutes, calves, lower back",
        "Control the eccentric phase slowly to maximize hamstring activation and reduce injury risk.",
        "Intermediate to advanced"
    )


)

@OptIn(ExperimentalLayoutApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomePage(
    navController: NavController,
    username: String,
    isDarkMode: Boolean = true,
    onThemeChange: (Boolean) -> Unit = {}
) {

    val colors = MaterialTheme.colorScheme
    val userViewModel: UserViewModel = viewModel()
    
    var currentUser by remember { mutableStateOf<Users?>(null) }
    
    LaunchedEffect(username) {
        userViewModel.getUserByUsername(username) { user ->
            currentUser = user
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedMuscle by remember { mutableStateOf<MuscleInfo?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var use3DModel by rememberSaveable { mutableStateOf(false) }
    var modelLoadFailed by rememberSaveable { mutableStateOf(false) }
    var selectedExerciseIndex by remember {
        mutableIntStateOf(0)
    }
    var bmiHeightCm by rememberSaveable { mutableStateOf("") }
    var bmiWeightKg by rememberSaveable { mutableStateOf("") }
    var bmiResult by rememberSaveable { mutableStateOf<String?>(null) }
    var waterLiters by rememberSaveable { mutableStateOf("") }

    val filteredMuscles = muscleDatabase.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(futuristicBackgroundBrush(isDarkMode)),
        //scaffold koristi boju pozadine iz teme da modovi rade kako treba da ne bzude bugova
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {

            val themeRotation by animateFloatAsState(targetValue = if (!isDarkMode) 0f else 180f, animationSpec = tween(durationMillis = ShortAnimationDuration))
                        IconButton(onClick = { onThemeChange(!isDarkMode) }) {
                            Text("☀️", modifier = Modifier.rotate(themeRotation))
                        }

            Box(
                modifier = Modifier.weight(1f).height(40.dp)
                    .background(
                        colors.surface,
                        RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    singleLine = true,
                    decorationBox = {
                        if (searchQuery.isEmpty()) {
                            Text("Search muscles", color = Color.Gray)
                        }
                        it()
                    }
                )
            }

            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }

            IconButton(onClick = {
                navController.navigate("ProfilePage/$username")
            }) {
                Icon(Icons.Default.Person, null)
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                title = {
                    Text("💬 Health Tips", fontWeight = FontWeight.Bold)
                },
                text = {
                    val randomTip = chatbotResponses.random()
                    Column {
                        Text(randomTip, fontSize = 14.sp, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(12.dp))
                        Text("Muscles v1.0", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            )
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Text("Find Muscles", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                            val selected3D = use3DModel
                            val selectedBMI = !use3DModel && !modelLoadFailed
                            val scale3D by animateFloatAsState(targetValue = if (selected3D) 1.03f else 1f, animationSpec = tween(durationMillis = ShortAnimationDuration))
                            val scaleBMI by animateFloatAsState(targetValue = if (selectedBMI) 1.03f else 1f, animationSpec = tween(durationMillis = ShortAnimationDuration))
                            val scaleWorkout by animateFloatAsState(targetValue = 1f, animationSpec = tween(durationMillis = ShortAnimationDuration))

                            Button(
                                onClick = {
                                    use3DModel = true
                                    modelLoadFailed = false
                                },
                                modifier = Modifier.weight(1f).scale(scale3D),
                                colors = futuristicButtonColors(colors.primary)
                            ) {
                                Text("3D Model")
                            }

                            OutlinedButton(
                                onClick = {
                                    use3DModel = false
                                    modelLoadFailed = false
                                },
                                modifier = Modifier.weight(1f).scale(scaleBMI)
                            ) {
                                Text("BMI Calculator")
                            }

                            Button(
                                onClick = { navController.navigate("Exercise/$username") },
                                modifier = Modifier.weight(1f).scale(scaleWorkout),
                                colors = futuristicButtonColors(colors.primary)
                            ) {
                                Text("Workout")
                            }
                        }

            if (modelLoadFailed) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "3D model failed to load, showing BMI calculator.",
                    color = colors.error,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth().height(520.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(colors.surface.copy(alpha = 0.92f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {

                val show3D = use3DModel && !modelLoadFailed
                AnimatedContent(
                    targetState = show3D,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(durationMillis = MediumAnimationDuration)) + scaleIn(animationSpec = tween(durationMillis = MediumAnimationDuration), initialScale = 0.98f)) togetherWith
                        (fadeOut(animationSpec = tween(durationMillis = MediumAnimationDuration)) + scaleOut(animationSpec = tween(durationMillis = MediumAnimationDuration), targetScale = 0.98f))
                    },
                    label = "3DModelAnimation"
                ) { show ->
                                    if (show) {
                                        HumanBody3D(
                                            onMuscleClicked = { muscleId: String ->
                                                Log.d("HomePage", "Selected muscle from 3D model: $muscleId")

                                                val muscle = muscleDatabase.find {
                                                    it.id.equals(muscleId.trim(), ignoreCase = true)
                                                }

                                                if (muscle != null) {
                                                    selectedMuscle = muscle
                                                    selectedExerciseIndex = 0
                                                    Log.d("HomePage", "Loaded muscle info: ${muscle.name}")
                                                } else {
                                                    Log.e("HomePage", "Muscle not found: $muscleId")
                                                }
                                            },
                                            onModelFailed = {
                                                modelLoadFailed = true
                                                use3DModel = false
                                            }
                                        )
                                    } else {
                                        BMICalculatorCard(
                                            heightText = bmiHeightCm,
                                            weightText = bmiWeightKg,
                                            bmiResult = bmiResult,
                                            isDarkMode = isDarkMode,
                                            onHeightChange = {
                                                bmiHeightCm = it
                                bmiResult = null
                            },
                                            onWeightChange = {
                                                bmiWeightKg = it
                                                bmiResult = null
                                            },
                                            onCalculate = {
                                                val height = bmiHeightCm.toFloatOrNull()
                                                val weight = bmiWeightKg.toFloatOrNull()

                                                bmiResult = if (height == null || weight == null || height <= 0f || weight <= 0f) {
                                                    "Enter valid height and weight"
                                                } else {
                                                    val heightM = height / 100f
                                                    val bmi = weight / (heightM * heightM)
                                                    val category = when {
                                                        bmi < 18.5f -> "Underweight"
                                                        bmi < 25f -> "Normal"
                                                        bmi < 30f -> "Overweight"
                                                        else -> "Obese"
                                                    }
                                                    "BMI: ${String.format(Locale.US, "%.1f", bmi)} ($category)"
                                                }
                                            }
                                        )
                                    }
                                }
            }

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(colors.surface.copy(alpha = 0.92f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "💧 Daily Water Intake",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )
                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = waterLiters,
                            onValueChange = { waterLiters = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Liters") },
                            singleLine = true,
                            colors = futuristicTextFieldColors(isDarkMode, colors.primary),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                            )
                        )

                        Button(
                            onClick = {
                                if (waterLiters.isNotEmpty() && currentUser != null) {
                                    val liters = waterLiters.toDoubleOrNull() ?: 0.0
                                    if (liters > 0) {
                                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                                        val currentDate = dateFormat.format(Date())
                                        userViewModel.recordWaterIntake(currentUser!!.id, liters, currentDate)
                                        waterLiters = ""
                                    }
                                }
                            },
                            modifier = Modifier.height(56.dp),
                            colors = futuristicButtonColors(colors.primary)
                        ) {
                            Text("Save")
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Enter how much water you drank today",
                        fontSize = 12.sp,
                        color = colors.onSurfaceVariant
                    )
                }
            }

            selectedMuscle?.let { muscle ->
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.surfaceVariant.copy(alpha = 0.9f), RoundedCornerShape(24.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        muscle.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(muscle.function, color = colors.onSurfaceVariant, fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Primary movement: ${muscle.primaryMovement}", color = colors.onSurfaceVariant, fontSize = 12.sp)
                    Text("Secondary muscles: ${muscle.secondaryMuscles}", color = colors.onSurfaceVariant, fontSize = 12.sp)
                    Text("Difficulty: ${muscle.difficulty}", color = colors.onSurfaceVariant, fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(muscle.trainingTip, color = colors.onSurfaceVariant, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(16.dp))


            selectedMuscle?.let { muscle ->

                Text(
                    "Exercises:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                val context = LocalContext.current

                val imageLoader = remember(context) {

                    ImageLoader.Builder(context)
                        .components {
                            add(ImageDecoderDecoder.Factory())
                        }
                        .build()
                }

                LaunchedEffect(muscle.id) {

                    selectedExerciseIndex = 0

                    muscle.exercises.forEach { ex ->

                        try {

                            val req = ImageRequest.Builder(context)
                                .data(ex.gif)
                                .build()

                            imageLoader.enqueue(req)

                        } catch (e: Exception) {

                            Log.w(
                                "GifPrefetch",
                                "Prefetch failed for ${ex.gif}: ${e.message}"
                            )
                        }
                    }
                }

                Column {

                    muscle.exercises.forEachIndexed { index, ex ->

                        key("${muscle.id}_${ex.name}_$index") {

                                                    AnimatedVisibility(
                                                        visible = true,
                                                        enter = fadeIn(animationSpec = tween(240)) + slideInHorizontally(animationSpec = tween(240)) { fullWidth -> fullWidth / 4 },
                                                        exit = fadeOut(animationSpec = tween(180))
                                                    ) {
                                                        ExerciseCard(
                                                            exercise = ex,
                                                            imageLoader = imageLoader,
                                                            colors = colors
                                                        )
                                                    }
                                                }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))


            Text("Search results", fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))


            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                filteredMuscles.forEach { muscle ->
                    Box(
                        modifier = Modifier
                            .background(colors.primary, RoundedCornerShape(20.dp))
                            .clickable { selectedMuscle = muscle }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(muscle.name, color = Color.White)
                    }
                }
            }
            Spacer(
                Modifier.height(40.dp)
            )
        }
    }
}

}

@Composable
fun BMICalculatorCard(
    heightText: String,
    weightText: String,
    bmiResult: String?,
    isDarkMode: Boolean = true,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onCalculate: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "BMI Calculator",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary
                )
                Text(
                    text = "Unesi visinu i težinu da izračunaš BMI.",
                    fontSize = 12.sp,
                    color = colors.onSurfaceVariant
                )

                OutlinedTextField(
                    value = heightText,
                    onValueChange = onHeightChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Visina (cm)") },
                    singleLine = true,
                    colors = futuristicTextFieldColors(isDarkMode, colors.primary)
                )

                OutlinedTextField(
                    value = weightText,
                    onValueChange = onWeightChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Težina (kg)") },
                    singleLine = true,
                    colors = futuristicTextFieldColors(isDarkMode, colors.primary)
                )

                Button(
                    onClick = onCalculate,
                    modifier = Modifier.fillMaxWidth(),
                    colors = futuristicButtonColors(colors.primary)
                ) {
                    Text("Calculate BMI")
                }

                bmiResult?.let {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = colors.primary.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.primary
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HumanBody3D(
    onModelFailed: () -> Unit = {},
    onMuscleClicked: (String) -> Unit = {}
) {

    val currentOnMuscleClicked by rememberUpdatedState(
        newValue = onMuscleClicked
    )

    val context = LocalContext.current

    val webView = remember {

        WebView(context).apply {

            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            )

            setLayerType(
                android.view.View.LAYER_TYPE_HARDWARE,
                null
            )

            setBackgroundColor(
                android.graphics.Color.TRANSPARENT
            )

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            settings.allowFileAccess = true
            settings.allowContentAccess = true

            settings.loadsImagesAutomatically = true

            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            // pinch zoom za bolji pregled modela bez dodatnih UI elemenata
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false

            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false

            settings.cacheMode = WebSettings.LOAD_DEFAULT

            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false

            webChromeClient = WebChromeClient()

                webViewClient = object : WebViewClient() {

                override fun onPageFinished(
                    view: WebView?,
                    url: String?
                ) {

                    super.onPageFinished(view, url)

                    Log.d(
                        "HumanBody3D",
                        "PAGE LOADED: $url"
                    )

                    evaluateJavascript(
                        """
                        document.body.style.margin='0';
                        document.body.style.height='100vh';
                        document.documentElement.style.height='100vh';
                        document.body.style.overflow='hidden';
                        """.trimIndent(),
                        null
                    )
                }

                    override fun onReceivedError(
                        view: WebView?,
                        request: android.webkit.WebResourceRequest?,
                        error: android.webkit.WebResourceError?
                    ) {

                        super.onReceivedError(view, request, error)

                        Log.e(
                            "HumanBody3D",
                            "ERROR: ${error?.description}"
                        )

                        onModelFailed()
                    }
            }

            addJavascriptInterface(

                object {

                    @Suppress("UNUSED")
                    @android.webkit.JavascriptInterface
                    fun onMuscleClicked(
                        muscleId: String
                    ) {

                        android.os.Handler(
                            android.os.Looper.getMainLooper()
                        ).post {

                            Log.d(
                                "HumanBody3D",
                                "Selected muscle from 3D model: $muscleId"
                            )

                            currentOnMuscleClicked(
                                muscleId.trim()
                            )
                        }
                    }

                },

                "Android"
            )

            loadUrl(
                "file:///android_asset/body_3d.html"
            )
        }
    }

    DisposableEffect(Unit) {

        onDispose {

            webView.stopLoading()
            webView.destroy()
        }
    }

    AndroidView(

        modifier = Modifier.fillMaxSize(),

        factory = {

            webView
        },

        update = {

            it.invalidate()
        }
    )
}

@Composable
fun ExerciseCard(exercise: Exercise, imageLoader: ImageLoader, colors: ColorScheme) {
    val context = LocalContext.current
    var reloadKey by remember { mutableStateOf(0) }
    var currentUrl by remember { mutableStateOf(exercise.gif) }
    var triedAlternate by remember { mutableStateOf(false) }
    var showPlaceholder by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface.copy(alpha = 0.92f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                exercise.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colors.primary
            )

            Spacer(Modifier.height(8.dp))

  //aspect radio 16:9 da gifovi ne budu sasjeceni
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(colors.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                val model = remember(reloadKey, currentUrl) {
                    ImageRequest.Builder(context)
                        .data(currentUrl)
                        .crossfade(300)
                        .allowHardware(false)
                        .build()
                }

                    SubcomposeAsyncImage(
                    model = model,
                    contentDescription = exercise.name,
                    imageLoader = imageLoader,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(18.dp)),

                    contentScale = ContentScale.Fit,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = colors.primary
                        )
                    },
                    error = {
                        if (showPlaceholder) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_gif_placeholder),
                                contentDescription = "placeholder",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "GIF not available",
                                    color = colors.onSurfaceVariant,
                                    fontSize = 12.sp
                                )
                                Spacer(Modifier.height(6.dp))
                                Button(onClick = {
                                    // manual retry: reset states
                                    triedAlternate = false
                                    showPlaceholder = false
                                    currentUrl = exercise.gif
                                    reloadKey++
                                }, colors = ButtonDefaults.buttonColors(containerColor = colors.primary)) {
                                    Text("Retry")
                                }
                            }
                        }
                    },
                    onError = { state: coil.compose.AsyncImagePainter.State.Error ->
                        val msg = state.result.throwable.message ?: "unknown"
                        Log.e("GifLoader", "Failed to load GIF: $currentUrl, Error: $msg")


                        if (!triedAlternate) {
                            triedAlternate = true
                            val alt = deriveAlternateGifUrl(currentUrl)
                            if (alt != null && alt != currentUrl) {
                                Log.d("GifLoader", "Trying alternate GIF URL: $alt")
                                currentUrl = alt
                                reloadKey++
                            } else {
                                showPlaceholder = true
                            }
                        } else {
                            showPlaceholder = true
                        }
                    }
                )
            }
        }
    }
}


fun deriveAlternateGifUrl(url: String): String? {
    try {

        val m = Regex("/media/([^/]+)/").find(url)
        if (m != null) {
            val id = m.groupValues[1]
            return "https://media.giphy.com/media/$id/giphy.gif"
        }

        // Try replacing size suffixes
        if (url.contains("/200w.gif")) return url.replace("/200w.gif", "/giphy.gif")
        if (url.contains("/200.gif")) return url.replace("/200.gif", "/giphy.gif")

        return null
    } catch (e: Exception) {
        return null
    }
}

@Suppress("UNUSED")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HumanBodySVG(onMuscleClick: (String) -> Unit) {
    val colors = MaterialTheme.colorScheme
    var isFront by remember { mutableStateOf(true) }
    var highlight by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { isFront = true },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    if (isFront) colors.primary else colors.surface
                )
            ) {
                Text("Front", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { isFront = false },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    if (!isFront) colors.primary else colors.surface
                )
            ) {
                Text("Back", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
                .background(Color(0xFFF4F6FA), RoundedCornerShape(16.dp))
                .pointerInput(isFront) {
                    detectTapGestures { offset ->
                        val x = offset.x / size.width
                        val y = offset.y / size.height

                        Log.d("BodyTap", "Tap at x=$x, y=$y")

                        val muscle = if (isFront) {
                            when {
                                y < 0.15f -> "chest"
                                y < 0.35f && x in 0.2f..0.8f -> "chest"
                                y < 0.55f && x in 0.3f..0.7f -> "abs"
                                y < 0.45f && x < 0.3f -> "biceps"
                                y < 0.45f && x > 0.7f -> "biceps"
                                y < 0.85f -> "quads"
                                else -> "calves"
                            }
                        } else {
                            when {
                                y < 0.25f -> "back"
                                y < 0.50f -> "back"
                                y < 0.75f -> "back"
                                else -> "calves"
                            }
                        }
                        highlight = muscle
                        onMuscleClick(muscle)
                    }
                }
        ) {
            Image(
                painter = painterResource(
                    if (isFront) R.drawable.body_front else R.drawable.body_back
                ),
                contentDescription = "Human Body",
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )

            if (highlight != null) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(colors.primary.copy(alpha = 0.15f))
                )
            }
        }

        Text(
            text = "👆 Tap on body regions to learn more",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )
    }
}
