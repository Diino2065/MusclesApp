#  MUSCLES - Tehnička Dokumentacija

**Verzija**: 1.0
**Platforma**: Android (Compose framework)
**Minimum SDK**: API 24
**Target SDK**: API 34
**Datum**: Maj 2026

---

##  Table of Contents

1. [Arhitektura](#arhitektura)
2. [Tehnološki Stack](#tehnološki-stack)
3. [Struktura Projekta](#struktura-projekta)
4. [Baza Podataka](#baza-podataka)
5. [Ključne Komponente](#ključne-komponente)
6. [API Reference](#api-reference)
7. [Integracijske Tačke](#integracijske-točke)
8. [Build & Deployment](#build--deployment)
9. [Debugging](#debugging)

---

##  Arhitektura

Aplikacija koristi **MVVM (Model-View-ViewModel)** arhitekturu sa Room bazom podataka za lokalno skladištenje.

```
┌─────────────────┐
│   UI Layer      │ (Compose Screens)
├─────────────────┤
│  ViewModel      │ (UserViewModel)
├─────────────────┤
│  Repository     │ (UserDao, UserViewModel)
├─────────────────┤
│  Local Database │ (Room)
└─────────────────┘
```

### Data Flow

1. **UI** → Korisnik klikne na element
2. **ViewModel** → Hvata akciju i ažurira state
3. **Repository/DAO** → Izvršava database query
4. **Database** → Čuva/dohvaća podatke
5. **ViewModel** → Vraća podatke UI-u
6. **UI** → Prikazuje rezultat

---

##  Tehnološki Stack

### Kotlin & Compose
- **Kotlin 1.8+** - Glavni programski jezik
- **Jetpack Compose** - Deklarativni UI framework
- **Material Design 3** - UI komponente

### Database & Storage
- **Room ORM** - SQLite interfejs
- **SQLite** - Lokalna baza podataka

### Image Loading
- **Coil** - Asinkroni loading slika/GIF-ova
- **ImageDecoder** - Dekodiranje GIF-ova na Android 13+

### Navigation
- **Jetpack Navigation** - Routing između screen-a
- **NavController** - Upravljanje navigacijom

### Animation
- **Crossfade** - Glatke animacije između UI stanja

### 3D Rendering
- **WebView** - Učitavanje HTML/JavaScript
- **Body_3D.html** - Three.js 3D model (ako je dostupan)

### Additional Libraries
- **Jetpack Lifecycle** - Upravljanje lifecycle-om
- **Kotlin Coroutines** - Asinkroni operacije
- **RememberSaveable** - Salviranje state-a kroz config changes

---

##  Struktura Projekta

```
Muscles/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── assets/
│   │   │   │   ├── body_3d.html          # 3D model HTML
│   │   │   │   ├── human.glb              # 3D model asset
│   │   │   │   └── gifs/                  # Exercise GIF-ovi
│   │   │   ├── java/com/example/muscles/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── screens/
│   │   │   │   │   ├── HomePage.kt        # Home screen + water tracking
│   │   │   │   │   ├── ProfilePage.kt     # User profile
│   │   │   │   │   ├── Stats.kt           # Statistics & analytics
│   │   │   │   │   └── LoginPage.kt       # Authentication
│   │   │   │   ├── RoomDb/
│   │   │   │   │   ├── Users.kt           # User entity
│   │   │   │   │   ├── WaterIntake.kt     # Water tracking entity
│   │   │   │   │   ├── MuscleSearch.kt    # Search history entity
│   │   │   │   │   ├── UserDao.kt         # Database operations
│   │   │   │   │   ├── UserViewModel.kt   # ViewModel
│   │   │   │   │   └── UserDatabase.kt    # Database config
│   │   │   │   └── ui/theme/
│   │   │   │       └── Color.kt           # Theme colors
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       ├── layout/
│   │   │       └── values/
│   │   ├── test/
│   │   └── androidTest/
│   └── build/
├── gradle/
│   └── libs.versions.toml
├── gradle.properties
├── settings.gradle.kts
├── build.gradle.kts
├── gradlew & gradlew.bat
└── README.md & TECHNICAL_DOCUMENTATION.md
```

---

##  Baza Podataka

### Room Database Setup

**Verzija**: 4
**Entiteti**: Users, WaterIntake, MuscleSearch

#### 1. Users Entity

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

**Kolone**:
- `id` - Primarni ključ (auto-increment)
- `username` - Jedinstveno korisničko ime
- `password` - Hashirana lozinka
- `totalTimeSpentSeconds` - Ukupno vrijeme provedeno u aplikaciji
- `lastSessionStart` - Vremenski pečat početka sesije

#### 2. WaterIntake Entity

```kotlin
@Entity(
    tableName = "water_intake",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WaterIntake(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val liters: Double,
    val date: String,  // "yyyy-MM-dd"
    val timestamp: Long = System.currentTimeMillis()
)
```

**Svrha**: Procesuiranje dnevnog unosa vode sa datumima

#### 3. MuscleSearch Entity

```kotlin
@Entity(tableName = "muscle_searches")
data class MuscleSearch(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val muscleName: String,
    val searchDate: Long = System.currentTimeMillis()
)
```

**Svrha**: Viđenje najčešće pretraživanih mišića

### DAO Methods

#### UserDao.kt

```kotlin
// User registration & authentication
suspend fun register(user: Users)
suspend fun login(username: String, password: String): Users?

// Profile management
suspend fun getUserByUsername(username: String): Users?
suspend fun updateUserProfile(...): Int

// Water intake
suspend fun recordWaterIntake(waterIntake: WaterIntake)
suspend fun getWaterIntakeForDate(userId: Int, date: String): WaterIntake?
suspend fun getWaterIntakeHistory(userId: Int): List<WaterIntake>
suspend fun updateWaterIntake(userId: Int, date: String, liters: Double): Int

// Stats
suspend fun getTotalTimeSpentSeconds(username: String): Long?
suspend fun updateTotalTime(username: String, seconds: Long)
```

---

##  Ključne Komponente

### 1. HomePage.kt - Glavni ekran

**Odgovornosti**:
- Prikazivanje 3D modela sa interaktivnim klikanjem
- BMI kalkulator
- Water intake tracking
- Search funkcionlnost
- Health tips chatbot
- Muscle info display

**State Management**:
```kotlin
var searchQuery by remember { mutableStateOf("") }
var selectedMuscle by remember { mutableStateOf<MuscleInfo?>(null) }
var waterLiters by rememberSaveable { mutableStateOf("") }
var use3DModel by rememberSaveable { mutableStateOf(false) }
```

**Ključne Funkcije**:
- `BMICalculatorCard()` - BMI računanje
- `HumanBody3D()` - WebView sa 3D modelom
- `ExerciseCard()` - Prikaz exercise GIF-ova

### 2. Stats.kt - Statistika

**Odgovornosti**:
- Prikazivanje vremena provedenog u aplikaciji
- Historija unosa vode
- Najčešće pretraživani mišići
- Live vrijeme koje se ažurira svaké sekunde

**Ključne Funkcije**:
```kotlin
fun StatsCard(totalTime: String, isDarkMode: Boolean)
fun MostSearchedMuscles(muscles: List<Pair<String, Int>>, isDarkMode: Boolean)
fun WaterIntakeStats(waterIntakeHistory: List<WaterIntake>, isDarkMode: Boolean)
```

### 3. ProfilePage.kt - Profil

**Odgovornosti**:
- Prikaz korisničkih podataka
- Izmjena profila
- Brisanje/ažuriranje informacija
- Sigurne navigacijske dugme sa inset padding

### 4. UserViewModel.kt - Business Logic

**Ključne Metode**:
```kotlin
// User operations
fun registerUser(user: Users, onResult: (Boolean) -> Unit)
fun loginUser(username: String, password: String, onResult: (Users?) -> Unit)
fun getUserByUsername(username: String, onResult: (Users?) -> Unit)
fun updateUserProfile(..., onResult: (Boolean) -> Unit)

// Water intake
fun recordWaterIntake(userId: Int, liters: Double, date: String)
fun getWaterIntakeHistory(userId: Int, onResult: (List<WaterIntake>) -> Unit)

// Stats
fun getTotalTimeSeconds(username: String, onResult: (Long) -> Unit)
fun updateTotalTime(username: String, seconds: Long)
```

---

##  API Reference

### Chatbot Responses

```kotlin
private val chatbotResponses = listOf(
    " Pij najmanje 2-3 litre vode dnevno za bolji metabolism!",
    " Konzumiraj više proteina nakon treninga za bolje mišićne rezultate!",
    " Redovna vježbanja 30 minuta dnevno čini razliku u zdravlju!",
    ...more tips
)
```

### Muscle Database

```kotlin
data class MuscleInfo(
    val id: String,           // "biceps", "triceps", etc.
    val name: String,         // "Biceps Brachii"
    val function: String,     // Detaljne funkcije
    val exercises: List<Exercise>,  // Vježbe sa GIF-ovima
    val primaryMovement: String,
    val secondaryMuscles: String,
    val trainingTip: String,
    val difficulty: String
)
```

### Exercise Structure

```kotlin
data class Exercise(
    val name: String,     // "Dumbbell Curl"
    val gif: String       // "file:///android_asset/gifs/dumbbell_curl.gif"
)
```

---

##  Integracijske Tačke

### WebView Integration (3D Model)

```kotlin
webView.apply {
    settings.javaScriptEnabled = true
    addJavascriptInterface(
        object {
            @JavascriptInterface
            fun onMuscleClicked(muscleId: String) {
                // Poziva se iz JavaScript-a
                currentOnMuscleClicked(muscleId)
            }
        },
        "Android"
    )
    loadUrl("file:///android_asset/body_3d.html")
}
```

### Image Loading (Coil)

```kotlin
val imageLoader = ImageLoader.Builder(context)
    .components {
        add(ImageDecoderDecoder.Factory())  // GIF support
    }
    .build()

SubcomposeAsyncImage(
    model = imageUrl,
    imageLoader = imageLoader,
    contentScale = ContentScale.Fit,
    loading = { CircularProgressIndicator(...) },
    error = { /* error handling */ }
)
```

### Navigation

```kotlin
// Navigate to profile
navController.navigate("ProfilePage/$username")

// Navigate to stats
navController.navigate("Stats/$username")

// Navigate back to home
navController.navigate("HomePage/$username")
```

---

##  Build & Deployment

### Build Variants

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

### ProGuard Configuration

Konfigurirano u `app/proguard-rules.pro` za optimizaciju

### Dependencies

Ključne zavisnosti iz `build.gradle.kts`:
- Jetpack Compose
- Room Database
- Coil Image Loading
- Navigation Compose
- Lifecycle components
- Kotlin Coroutines

---

##  Debugging

### Logovanje

Koristi Android Log za debugging:

```kotlin
Log.d("HomePage", "Selected muscle: ${muscle.name}")
Log.e("HumanBody3D", "Error loading model: ${error?.description}")
Log.w("GifLoader", "Failed to load GIF: ${currentUrl}")
```

### Česti Problemi

1. **GIF ne učita se**
   - Provjeri path fajlu
   - Async loading može trebati vrijeme
   - Koristi fallback placeholder

2. **3D model ne učita se**
   - WebView može vratiti grešku
   - Aplikacija prebacuje na BMI kalkulator
   - Provjeri body_3d.html validnost

3. **State loss pri rotaciji ekrana**
   - Koristi `rememberSaveable` umjesto `remember`
   - State se očuva kroz lifecycle

4. **Database migration failure**
   - `fallbackToDestructiveMigration()` briše podatke
   - Za production: Napravite proper migration

### Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

---


---



