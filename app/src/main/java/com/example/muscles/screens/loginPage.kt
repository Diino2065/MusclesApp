package com.example.muscles.screens
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.muscles.RoomDb.UserViewModel
import com.example.muscles.ui.theme.DarkBackground
import com.example.muscles.ui.theme.DarkPrimary
import com.example.muscles.ui.theme.DarkOnBackground
import com.example.muscles.ui.theme.futuristicBackgroundBrush
import com.example.muscles.ui.theme.futuristicButtonColors
import com.example.muscles.ui.theme.futuristicTextFieldColors
import com.example.muscles.utils.InputValidator
import com.example.muscles.utils.AppLogger
import coil.compose.rememberAsyncImagePainter



@Composable
fun LoginPage(navController: NavController, userViewModel: UserViewModel) {

    val context = LocalContext.current
    val viewModel: UserViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
    )

    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(futuristicBackgroundBrush(true))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(32.dp),
                color = Color.White.copy(alpha = 0.06f),
                shadowElevation = 18.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Muscles",
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 52.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif
                    )

                    Text(
                        text = "You can never know enough about the human body!",
                        modifier = Modifier.padding(bottom = 28.dp),
                        color = DarkOnBackground,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("Username") },
                        singleLine = true,
                        colors = futuristicTextFieldColors(true, DarkPrimary),
                        shape = RoundedCornerShape(18.dp)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 18.dp),
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        colors = futuristicTextFieldColors(true, DarkPrimary),
                        shape = RoundedCornerShape(18.dp)
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color(0xFFFF6B6B),
                            modifier = Modifier.padding(bottom = 12.dp),
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = {
                            errorMessage = ""
                            
                            // Validate username
                            val usernameValidation = InputValidator.isValidUsername(userName)
                            if (!usernameValidation.first) {
                                errorMessage = usernameValidation.second
                                AppLogger.logWarning("Username validation failed: ${usernameValidation.second}")
                                return@Button
                            }
                            
                            // Validate password
                            if (password.isEmpty()) {
                                errorMessage = "Password cannot be empty"
                                return@Button
                            }
                            
                            if (userName.isNotEmpty() && password.isNotEmpty()) {
                                isLoading = true
                                AppLogger.logAction("LOGIN_ATTEMPT", "Username: $userName")
                                userViewModel.loginUser(userName, password) { user ->
                                    isLoading = false
                                    if (user != null) {
                                        AppLogger.logUserAction(user.id, "SUCCESSFUL_LOGIN")
                                        navController.navigate("HomePage/${user.username}")
                                    } else {
                                        errorMessage = "Invalid username or password"
                                        AppLogger.logWarning("Login failed - invalid credentials")
                                    }
                                }
                            } else {
                                errorMessage = "Please fill in all fields"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(bottom = 16.dp),
                        colors = futuristicButtonColors(DarkPrimary),
                        shape = RoundedCornerShape(18.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Login",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            color = Color(0xFFB0B0C0),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Register",
                            modifier = Modifier.clickable {
                                navController.navigate("registerPage")
                            },
                            color = DarkPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
