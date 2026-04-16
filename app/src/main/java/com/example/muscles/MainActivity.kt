package com.example.muscles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.example.muscles.screens.ProfilePage
import com.example.muscles.screens.RegisterPage
import com.example.muscles.screens.Stats
import com.example.muscles.screens.loginPage
import com.example.muscles.ui.theme.MusclesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusclesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userViewModel: UserViewModel = viewModel(
                        factory = ViewModelProvider.AndroidViewModelFactory(application)
                    )
                    Navigation(navController, userViewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, userViewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "loginPage") {
        composable("loginPage") { loginPage(navController, userViewModel) }
        composable("registerPage") { RegisterPage(navController, userViewModel) }
        composable("HomePage/{username}") { backStackEntry ->
            HomePage(
                navController,
                username = backStackEntry.arguments?.getString("username").orEmpty()
            )
        }
        composable("ProfilePage/{username}") { backStackEntry ->
            ProfilePage(
                navController,
                userViewModel,
                username = backStackEntry.arguments?.getString("username").orEmpty()
            )
        }
        composable("Stats") { Stats(navController) }
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