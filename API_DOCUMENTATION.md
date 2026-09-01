#  API DOKUMENTACIJA - Muscles Android App

---

##  Sadržaj

1. [Composable Funkcije](#composable-funkcije)
2. [ViewModel API](#viewmodel-api)
3. [Database Models](#database-models)
4. [Navigation Routes](#navigation-routes)
5. [Theme & Colors](#theme--colors)
6. [Error Handling](#error-handling)

---

##  Composable Funkcije

### HomePage

```kotlin
@Composable
fun HomePage(
    navController: NavController,
    username: String,
    isDarkMode: Boolean = true,
    onThemeChange: (Boolean) -> Unit = {}
)
```

**Parametri**:
- `navController` - Navigation controller za routing
- `username` - Username trenutnog korisnika
- `isDarkMode` - Dark/light mode flag
- `onThemeChange` - Callback za promjenu teme

**State**:
- `searchQuery` - Search upite
- `selectedMuscle` - Odabrani mišić
- `waterLiters` - Entered water liters
- `use3DModel` - 3D model ili BMI kalkulator
- `totalSeconds` - Vremenska sesija

**Primjer**:
```kotlin
HomePage(
    navController = rememberNavController(),
    username = "john_doe",
    isDarkMode = true,
    onThemeChange = { isDark ->
        // Handle theme change
    }
)
```

---

### BMICalculatorCard

```kotlin
@Composable
fun BMICalculatorCard(
    heightText: String,
    weightText: String,
    bmiResult: String?,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onCalculate: () -> Unit
)
```

**Parametri**:
- `heightText` - Height u cm (string)
- `weightText` - Weight u kg (string)
- `bmiResult` - Rezultat BMI kalkulacija
- `onHeightChange` - Height input callback
- `onWeightChange` - Weight input callback
- `onCalculate` - Calculate button callback

**Primjer**:
```kotlin
BMICalculatorCard(
    heightText = "170",
    weightText = "70",
    bmiResult = "BMI: 24.2 (Normal)",
    onHeightChange = { height -> /* ... */ },
    onWeightChange = { weight -> /* ... */ },
    onCalculate = {
        val bmi = calculateBMI(height, weight)
    }
)
```

---

### HumanBody3D

```kotlin
@Composable
fun HumanBody3D(
    onModelFailed: () -> Unit = {},
    onMuscleClicked: (String) -> Unit = {}
)
```

**Parametri**:
- `onModelFailed` - Callback ako model ne učita
- `onMuscleClicked` - Callback sa mišićem ID-om

**JavaScript Interface**:
Aplikacija registrira Android interface za WebView:

```javascript
// U body_3d.html
Android.onMuscleClicked("biceps");
```

**Primjer**:
```kotlin
HumanBody3D(
    onModelFailed = {
        modelLoadFailed = true
        use3DModel = false
    },
    onMuscleClicked = { muscleId ->
        val muscle = muscleDatabase.find { it.id == muscleId }
        selectedMuscle = muscle
    }
)
```

---

### ExerciseCard

```kotlin
@Composable
fun ExerciseCard(
    exercise: Exercise,
    imageLoader: ImageLoader,
    colors: ColorScheme
)
```

**Parametri**:
- `exercise` - Exercise data sa GIF URL-om
- `imageLoader` - Coil ImageLoader instance
- `colors` - Material3 ColorScheme

**Primjer**:
```kotlin
val context = LocalContext.current
val imageLoader = ImageLoader.Builder(context)
    .components { add(ImageDecoderDecoder.Factory()) }
    .build()

ExerciseCard(
    exercise = Exercise(
        name = "Dumbbell Curl",
        gif = "file:///android_asset/gifs/dumbbell_curl.gif"
    ),
    imageLoader = imageLoader,
    colors = colors
)
```

---

### ProfilePage

```kotlin
@Composable
fun ProfilePage(
    navController: NavController,
    userViewModel: UserViewModel,
    username: String,
    isDarkMode: Boolean = true
)
```

**Funkcionlnosti**:
- Prikaz korisničkog profila
- Editiranje imena, emaila, bio-a
- Upload profilne slike
- Sigurne navigacijske dugme

---

### Stats

```kotlin
@Composable
fun Stats(
    navController: NavController,
    username: String,
    userViewModel: UserViewModel = viewModel(),
    isDarkMode: Boolean = true
)
```

**Prikazuje**:
- Total time spent sa live countera
- Water intake historija
- Most searched muscles

---

##  ViewModel API

### UserViewModel

```kotlin
class UserViewModel(application: Application) : AndroidViewModel(application)
```

#### User Management

```kotlin
// Register
fun registerUser(user: Users, onResult: (Boolean) -> Unit)

// Login
fun loginUser(
    username: String,
    password: String,
    onResult: (Users?) -> Unit
)

// Get user by username
fun getUserByUsername(username: String, onResult: (Users?) -> Unit)

// Update profile
fun updateUserProfile(
    username: String,
    name: String,
    email: String,
    bio: String?,
    profileImageUri: String?,
    onResult: (Boolean) -> Unit
)
```

#### Water Tracking

```kotlin
// Record water intake
fun recordWaterIntake(userId: Int, liters: Double, date: String)

// Get water intake za specifičan dan
fun getWaterIntakeForDate(userId: Int, date: String, onResult: (Double) -> Unit)

// Get history (zadnjih 30 dana)
fun getWaterIntakeHistory(userId: Int, onResult: (List<WaterIntake>) -> Unit)

// Update existing record
fun updateWaterIntake(userId: Int, date: String, liters: Double)
```

#### Session Tracking

```kotlin
// Get total time spent
fun getTotalTimeSeconds(username: String, onResult: (Long) -> Unit)

// Update total time
fun updateTotalTime(username: String, seconds: Long)

// Record muscle search
fun recordMuscleSearch(userId: Int, muscleName: String)

// Get most searched
fun getMostSearchedMuscles(userId: Int, onResult: (List<Pair<String, Int>>) -> Unit)
```

---

##  Database Models

### Users Entity

```kotlin
@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val bio: String? = null,
    val profileImageUri: String?,
    val totalTimeSpentSeconds: Long = 0,
    val lastSessionStart: Long = 0
)
```

**Primjer kreiranja**:
```kotlin
val newUser = Users(
    name = "John Doe",
    email = "john@example.com",
    username = "johndoe",
    password = "hashedPassword",
    bio = "Fitness enthusiast",
    profileImageUri = null,
    totalTimeSpentSeconds = 0,
    lastSessionStart = System.currentTimeMillis()
)
userViewModel.registerUser(newUser) { success ->
    if (success) {
        // User registered
    }
}
```

---

### WaterIntake Entity

```kotlin
@Entity(tableName = "water_intake")
data class WaterIntake(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val liters: Double,
    val date: String,  // yyyy-MM-dd format
    val timestamp: Long = System.currentTimeMillis()
)
```

**Primjer kreiranja**:
```kotlin
val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
val waterEntry = WaterIntake(
    userId = currentUser.id,
    liters = 2.5,
    date = today
)
userViewModel.recordWaterIntake(currentUser.id, 2.5, today)
```

---

### MuscleSearch Entity

```kotlin
@Entity(tableName = "muscle_searches")
data class MuscleSearch(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val muscleName: String,
    val searchDate: Long = System.currentTimeMillis()
)
```

---

### MuscleInfo Data Class

```kotlin
data class MuscleInfo(
    val id: String,              // "biceps"
    val name: String,            // "Biceps Brachii"
    val function: String,        // Detaljni opis
    val exercises: List<Exercise>,
    val primaryMovement: String,
    val secondaryMuscles: String,
    val trainingTip: String,
    val difficulty: String
)
```

**Primjer**:
```kotlin
val biceps = MuscleInfo(
    id = "biceps",
    name = "Biceps Brachii",
    function = "The biceps brachii is...",
    exercises = listOf(
        Exercise("Dumbbell Curl", "file:///android_asset/gifs/dumbbell_curl.gif"),
        Exercise("Barbell Curl", "file:///android_asset/gifs/barbell_curl.gif")
    ),
    primaryMovement = "Elbow flexion",
    secondaryMuscles = "Brachialis, forearms",
    trainingTip = "Use controlled repetitions...",
    difficulty = "Beginner to advanced"
)
```

---

### Exercise Data Class

```kotlin
data class Exercise(
    val name: String,  // "Dumbbell Curl"
    val gif: String    // "file:///android_asset/gifs/dumbbell_curl.gif"
)
```

---

##  Navigation Routes

```kotlin
// Home page
"HomePage/$username"

// Profile page
"ProfilePage/$username"

// Stats page
"Stats/$username"

// Login page
"LoginPage"

// Sign up page
"SignUpPage"
```

**Primjer navigacije**:
```kotlin
// Navigate to profile
navController.navigate("ProfilePage/johndoe")

// Navigate back
navController.popBackStack()

// Navigate and clear stack
navController.navigate("HomePage/johndoe") {
    popUpTo("LoginPage") { inclusive = true }
}
```

---

##  Theme & Colors

### Dark Mode Colors

```kotlin
val DarkBackground = Color(0xFF0F1419)
val DarkSurface = Color(0xFF1A202C)
val DarkPrimary = Color(0xFF4A9EFF)
val DarkOnBackground = Color(0xFFF5F5F5)
```

### Light Mode Colors

```kotlin
val LightBackground = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFFAFAFA)
val LightPrimary = Color(0xFF2563EB)
val LightOnBackground = Color(0xFF1F1F1F)
```

**Korištenje**:
```kotlin
val colors = MaterialTheme.colorScheme
Text(
    "Hello",
    color = if (isDarkMode)
        DarkOnBackground
    else
        LightOnBackground
)
```

---

##  Error Handling

### Try-Catch sa ViewModel

```kotlin
fun recordWaterIntake(userId: Int, liters: Double, date: String) {
    viewModelScope.launch {
        try {
            val waterIntake = WaterIntake(
                userId = userId,
                liters = liters,
                date = date
            )
            userDao.recordWaterIntake(waterIntake)
        } catch (e: Exception) {
            // Handle error silently, DB already has constraints
            Log.e("WaterIntake", "Failed to record: ${e.message}")
        }
    }
}
```

### 3D Model Fallback

```kotlin
HumanBody3D(
    onModelFailed = {
        modelLoadFailed = true
        use3DModel = false  // Fallback to BMI
    },
    onMuscleClicked = { muscleId ->
        // Handle click
    }
)
```

### Image Loading Error

```kotlin
SubcomposeAsyncImage(
    // ...
    error = {
        if (showPlaceholder) {
            Image(
                painter = painterResource(id = R.drawable.ic_gif_placeholder),
                contentDescription = "placeholder"
            )
        } else {
            Column {
                Text("GIF not available")
                Button(onClick = { /* retry */ }) {
                    Text("Retry")
                }
            }
        }
    }
)
```

---

## 🔐 Security Notes

 **Password Hashing**: Koristiti bcrypt 

 **WebView XSS**: Koristi `setJavaScriptEnabled = true` pažljivo

 **SQL Injection**: Room koristi prepared statements (safe)

---

##  Support

Za više info, pogledajte:
- `TECHNICAL_DOCUMENTATION.md` - Detaljne arhitekturne informacije
- `README.md` - User dokumentacija
- `QUICK_START.md` - Brz početak

---

**Zadnja ažuriranja**: Maj 2026

