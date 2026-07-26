#  CONTRIBUTING - Kako Doprinijeti Muscles Projektu


---

## 📋 Prije nego počnete

1. **Instalirajte Android Studio** 2023.1+
2. **Clone repozitorija**:
   ```bash
   git clone https://github.com/yourname/Muscles.git
   cd Muscles
   ```
3. **Sync Gradle zavisnosti**
4. **Pokrenite aplikaciju** na emulatoruILI fizičkom uređaju

---

## 🐛 Bug Reports

Ako pronađete bug:

1. **Provjerite da li već postoji** issue za taj bug
2. **Kreirajte novi Issue** sa:
   - Jasan naslov
   - Detaljne korake za reprodukciju
   - Expected vs actual ponašanje
   - Screenshot (ako je relevantno)
   - Device info (model, Android verzija)

### Primjer:

```
Title: 3D Model crashes when rotating phone too fast

Steps to reproduce:
1. Open app and go to Home page
2. Rotate your phone rapidly (10+ times)
3. App crashes

Expected: Model should continue to work smoothly
Actual: App crashes with NullPointerException

Device: Pixel 6, Android 13
```

---

## ✨ Feature Requests

Ako imate ideju za novu feature:

1. Otvorite **Discussion** (ne Issue!)
2. Dobijte feedback prije nego počnete sa kodom
3. Čekajte odobrenje od maintainera

---

## 💻 Kako Da Kodirате

### Code Style

```kotlin
// ✅ DOBRO - kotlin naming conventions
fun calculateBmi(height: Float, weight: Float): Float {
    val heightInMeters = height / 100
    return weight / (heightInMeters * heightInMeters)
}

// ❌ LOŠE - camelCase nije bio korišten
fun calculateBMI(HEIGHT: Float, WEIGHT: Float): Float {
    val h = HEIGHT / 100
    return WEIGHT / (h * h)
}
```

### Pravila

- Za **Kotlin** koristi **Kotlin Conventions**
- Maksimalno dužina linije: **100 karaktera**
- Koristi **meaningful variable names**
- Dodaj **dokumentacijske komentare** za complex logiku
- Koristi **val** umjesto **var** gdje je moguće

### Example sa Dokumentacijom

```kotlin
/**
 * Calculates BMI from height and weight
 *
 * @param heightInCm Height in centimeters
 * @param weightInKg Weight in kilograms
 * @return BMI value or null if invalid input
 */
fun calculateBMI(heightInCm: Float, weightInKg: Float): Float? {
    if (heightInCm <= 0 || weightInKg <= 0) return null
    val heightInMeters = heightInCm / 100
    return weightInKg / (heightInMeters * heightInMeters)
}
```

---

## 🔄 Git Workflow

### 1. Kreirajte Feature Branch

```bash
git checkout -b feature/add-water-intake-calendar
# ili
git checkout -b fix/3d-model-crash
# ili
git checkout -b docs/update-readme
```

### 2. Commit-ujte Vašu Promjenu

```bash
git add .
git commit -m "Add calendar view for water intake tracking"
```

**Commit Message Format**:
```
[TYPE] Brief description

Longer explanation if needed. Wrap at 72 characters.
Multiple paragraphs are okay.

Fixes #123
```

**Types**: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

### 3. Push i Kreirajte Pull Request

```bash
git push origin feature/add-water-intake-calendar
```

Otvorite PR sa:
- Jasan opis promjene
- Reference na povezani issue (`Fixes #123`)
- Screenshots za UI promjene
- Kopija checklist-a:

```markdown
## PR Checklist

- [ ] Kod slijedi style guide
- [ ] Dodao sam relevantne komentare
- [ ] Testirao sam na fizičkom uređaju
- [ ] Nema breaking changes
- [ ] Dokumentacija je ažurirana
```

---

## 🧪 Testing

Pre nego što submitujete PR:

```bash
# Format code
./gradlew ktlintFormat

# Run lint checks
./gradlew lint

# Run tests
./gradlew test

# Run instrumented tests on device
./gradlew connectedAndroidTest

# Build and test
./gradlew build
```

### Pisanje Testova

```kotlin
@RunWith(AndroidJUnit4::class)
class BMICalculatorTest {

    @Test
    fun calculateBmiCorrectly() {
        val height = 170f
        val weight = 70f
        val expectedBmi = 24.22f

        val result = calculateBmi(height, weight)

        assertEquals(expectedBmi, result, 0.1f)
    }

    @Test
    fun handleInvalidInput() {
        val result = calculateBmi(-170f, 70f)
        assertNull(result)
    }
}
```

---

## 📖 Dokumentacija

Ako dodajete novu feature, **mora biti dokumentovana**:

### 1. Code Comments

```kotlin
/**
 * Interaktivni 3D prikaz čovječjeg tijela
 *
 * Koristi WebView za učitavanje Three.js modela iz body_3d.html.
 * Klik na mišiće poziva [onMuscleClicked] callback.
 *
 * @param onMuscleClicked Pozvan kada korisnik klikne na mišić
 * @param onModelFailed Pozvan ako model ne može učitati
 */
@Composable
fun HumanBody3D(
    onModelFailed: () -> Unit = {},
    onMuscleClicked: (String) -> Unit = {}
) {
    // ...implementation
}
```

### 2. README Ažuriranje

Ako dodajete javnu feature, ažurirajte `README.md` sa:
- Kratkim opisom
- Kako koristiti
- Screenshot (ako je relevantno)

### 3. TECHNICAL_DOCUMENTATION

Za sistemske promjene, ažurirajte `TECHNICAL_DOCUMENTATION.md`:
- Arhitektonske promjene
- Nova database schema
- Integration points

---

## 🚀 Deployment

Trebate odobrenje maintainera da deployujete novu verziju:

```bash
# 1. Ažurirajte verziju u build.gradle.kts
versionCode = 2
versionName = "1.1"

# 2. Build release APK
./gradlew assembleRelease

# 3. APK je u: app/build/outputs/apk/release/app-release.apk

# 4. Sign APK sa keystore
# (maintainer će to obaviti sa production keystore-om)
```

---

## 🎯 Prioritetni Zadaci

Ako ne znate gdje početi, evo prioritetnih oblasti:

### 🔴 HIGH PRIORITY
- Password hashing (trenutno nema!)
- Bug fixes
- Performance optimizations
- Security issues

### 🟡 MEDIUM PRIORITY
- New features (sa odobrenja maintainera)
- Documentation improvements
- UI/UX enhancements
- Test coverage

### 🟢 LOW PRIORITY
- Code refactoring
- Minor style improvements
- Comment updates

---

## ❓ FAQ

**P: Trebam da fork-ujem ili da radim direktno na branch-u?**
A: Fork verzija i kreirajte PR. Direktan access je samo za maintainere.

**P: Koliko vremena je potrebno da se PR approve-a?**
A: Obično 2-3 dana, ovisno o kompleksnosti.

**P: Mogu li raditi na istoj feature-i kao netko drugi?**
A: Najbolje je da prvo pitati u Issue-u. Jedan issue = jedan developer.

**P: Šta ako je moj kod odbijena?**
A: Nema problema! Dobijete feedback i možete ispraviti. To je dio procesa.

**P: Trebam da budem eksperta za Android?**
A: Ne, ali trebate biti spreman da učite! Pytajte pitanja u PR komentarima.

---

## 📞 Kontakt

- Pitanja? Otvorite Discussion
- Brza podrška? Pinguite maintainera na issue-u
- Za chat, koristite Discord/Slack (ako postoji)

---

## 🙏 Hvala!

Svaki doprinos je dragocjenima - od malih bug fixes-a do velikih feature-a!

**Uživajte u razvoju na Muscles-u! 💪**


