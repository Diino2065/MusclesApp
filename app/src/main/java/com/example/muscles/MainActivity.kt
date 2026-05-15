package com.example.muscles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muscles.RoomDb.UserViewModel
import com.example.muscles.screens.HomePage
import com.example.muscles.screens.LoginPage
import com.example.muscles.screens.ProfilePage
import com.example.muscles.screens.RegisterPage
import com.example.muscles.screens.Stats
import com.example.muscles.screens.SplashScreen
import com.example.muscles.ui.theme.MusclesTheme

class MainActivity : ComponentActivity() {

    private var sessionStartTime: Long = 0
    private var currentUsername: String? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sessionStartTime = System.currentTimeMillis()
        setContent {
            val isDarkMode = remember { mutableStateOf(true) }

            MusclesTheme(darkTheme = isDarkMode.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val vm: UserViewModel = viewModel(
                        factory = ViewModelProvider.AndroidViewModelFactory(application)
                    )
                    userViewModel = vm
                    Navigation(navController, vm, isDarkMode.value, { isDarkMode.value = it }) { activeUsername ->
                        currentUsername = activeUsername
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (sessionStartTime > 0 && currentUsername != null && userViewModel != null) {
            val elapsedTimeSeconds = (System.currentTimeMillis() - sessionStartTime) / 1000
            userViewModel!!.getUserByUsername(currentUsername!!) { user ->
                if (user != null) {
                    userViewModel!!.updateSessionTime(user.id, elapsedTimeSeconds)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sessionStartTime = System.currentTimeMillis()
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    userViewModel: UserViewModel,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onUsernameActive: (String) -> Unit
) {
    NavHost(navController = navController, startDestination = "Splash") {
        composable("Splash") {
            SplashScreen(
                navController = navController,
                nextRoute = "loginPage"
            )
        }
        composable("loginPage") { LoginPage(navController, userViewModel) }
        composable("registerPage") { RegisterPage(navController, userViewModel) }
        composable("HomePage/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            if (username.isNotBlank()) onUsernameActive(username)
            HomePage(
                navController = navController,
                username = username,
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange
            )
        }
        composable("ProfilePage/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            if (username.isNotBlank()) onUsernameActive(username)
            ProfilePage(
                navController,
                userViewModel,
                username = username,
                isDarkMode = isDarkMode
            )
        }
        composable("Stats/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            if (username.isNotBlank()) onUsernameActive(username)
            Stats(
                navController = navController,
                username = username,
                userViewModel = userViewModel,
                isDarkMode = isDarkMode
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MusclesTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
        }
    }
}