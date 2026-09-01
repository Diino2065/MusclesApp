#  QUICK START - Brz Početak sa Muscles-om


---

## 1 Instalacija (5 minuta)

### Korak 1: Instalacija Android Studija
1. Preuzmite [Android Studio](https://developer.android.com/studio)
2. Instalirajte sa default opcijama
3. Pokrenite Android Studio

### Korak 2: Clone Projekta
```bash
# Terminal/Command Prompt
git clone https://github.com/yourusername/Muscles.git
cd Muscles
```

*Ili* - u Android Studio:
- File → New → Project from Version Control
- Unesite git URL
- Kliknite Clone

### Korak 3: Sync Gradle
Android Studio će automatski ponuditi:
```
Gradle files have changed since the last project sync...
```
Kliknite **"Sync Now"**

---

##  Pokretanje (3 minuta)

### Opcija A: Na Emulatoru
```bash
# Kreiraj emulator ako ga nemaš
./gradlew connectedAndroidTest

# Ili u Android Studio:
# 1. Tools → Device Manager
# 2. Create Device → Phone (Pixel 5, API 30+)
# 3. Start emulator
```

### Opcija B: Na Telefonu
```bash
# Omogući Developer Mode na phones-u
# 1. Settings → About Phone
# 2. Tap Build Number 7 puta
# 3. Settings → Developer Options → USB Debugging ON

# Connect USB i pokrenite
./gradlew installDebug

# Ili u Android Studio, kliknite zeleni Play button
```

### Pokretanje Aplikacije
```bash
# Terminal
./gradlew run

# Ili Android Studio
# Kliknite zeleni Play button ► (Run)
```

---

##  First Run (2 minuta)

### Kreiraj Account
1. Otvorite aplikaciju
2. Kliknite **Sign Up**
3. Unesite:
   - **Username**: `testuser`
   - **Email**: `test@example.com`
   - **Password**: `TestPass123`
4. Kliknite **Sign Up**

### Login
1. Na login ekranu, unesite iste kredencijale
2. Kliknite **Login**

### Prijeđi na Home Page
1. Kliknite **3D Model** ili **BMI Calculator**

---

##  Osnovna Upotreba

### Interaktivni 3D Model
```
1. Kliknite "3D Model" dugme
2. Kliknite na mišiće na tijelu
3. Vidite detaljni info desno
4. Scroll down da vidite vježbe
```

### BMI Kalkulator
```
1. Kliknite "BMI Calculator" dugme
2. Unesite:
   - Height (cm): 170
   - Weight (kg): 70
3. Kliknite "Calculate BMI"
```

### Water Tracking
```
1. Ispod BMI-ja, unesite vodu (litre)
   - Example: 2.5
2. Kliknite "Save"
3. Ide u Stats stranicu
```

### Health Tips
```
1. Kliknite zvonce 🔔 u gornjem desnom uglu
2. Vidite random savjet
3. Kliknite "OK" da zatvorite
```

### Stats Page
```
1. Na Profile stranici, kliknite "Stats"
2. Vidite:
   - Total time spent (live counter)
   - Water intake history
   - Most searched muscles
```

---

##  Gdje su Ključne Datoteke?

Za beginners, fokusirajte se na:

```
app/src/main/java/com/example/muscles/
├── screens/
│   ├── HomePage.kt           ← Główny ekran (START HERE!)
│   ├── ProfilePage.kt        ← User profile
│   ├── Stats.kt              ← Statistics
│   └── LoginPage.kt          ← Authentication
├── RoomDb/
│   ├── UserViewModel.kt      ← Business logic
│   ├── Users.kt              ← User entity
│   ├── WaterIntake.kt        ← Water tracking
│   └── UserDatabase.kt       ← Database setup
└── ui/theme/
    └── Color.kt              ← Colors & theming
```

**Za UI promjene**: Menjajte `HomePage.kt`
**Za logiku**: Menjajte `UserViewModel.kt`
**Za bazu**: Menjajte `RoomDb/` fajlove

---

##  Česti Problemi

### Problem: "Gradle sync failed"
```
Rješenje:
1. File → Invalidate Caches
2. Restart Android Studio
3. Sync Gradle ponovo
```

### Problem: "Emulator won't start"
```
Rješenje:
1. Check BIOS ima Virtualization enabled
2. Restore factory settings u Device Manager
3. Create new device
```

### Problem: "App crashes on startup"
```
Rješenje:
1. Provjeri Logcat (Window → Logcat)
2. Pogledaj error message
3. Provjeri internet konekciju
```

### Problem: "3D Model ne učitava se"
```
Rješenje:
1. To je normalno - WebView može biti spora
2. Aplikacija će prebaciti na BMI kalkulator
3. Sau body_3d.html trebale bi biti u assets/
```

### Problem: "Database error on first run"
```
Rješenje:
1. Uninstall aplikaciju
2. Build → Clean Project
3. Build → Rebuild Project
4. Run again
```

---

##  Running Tests

```bash
# Unit tests (local)
./gradlew test

# Device tests (instrumented)
./gradlew connectedAndroidTest

# Build project
./gradlew build

# Check lint errors
./gradlew lint
```

---

##  Debugging Tips

### 1. Logcat
```
Android Studio → View → Tool Windows → Logcat

Pogledaj output:
- D/ = Debug messages
- E/ = Error messages
- I/ = Info messages
```

### 2. Breakpoints
```
1. Kliknite na broj linije koda
2. Run → Debug App
3. Aplikacija će pauzirati na breakpoint-u
4. Inspect varijable
```

### 3. Android Profiler
```
View → Tool Windows → Profiler
- Memory usage
- CPU usage
- Network activity
- Battery drain
```





