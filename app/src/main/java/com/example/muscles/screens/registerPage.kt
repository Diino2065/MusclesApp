package com.example.muscles.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muscles.RoomDb.UserViewModel
import com.example.muscles.RoomDb.Users
import com.example.muscles.ui.theme.DarkBackground
import com.example.muscles.ui.theme.DarkPrimary
import com.example.muscles.ui.theme.DarkOnBackground
import com.example.muscles.ui.theme.futuristicBackgroundBrush
import com.example.muscles.ui.theme.futuristicButtonColors
import com.example.muscles.ui.theme.futuristicTextFieldColors


@Composable
fun RegisterPage(navController: NavController, userViewModel: UserViewModel) {

    var userName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val bio by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(futuristicBackgroundBrush(true))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))
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
                        text = "Create Account",
                        modifier = Modifier.padding(bottom = 12.dp),
                        color = Color.White,
                        fontSize = 44.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif
                    )

                    Text(
                        text = "Join the fitness community today",
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
                            .padding(bottom = 12.dp),
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        singleLine = true,
                        colors = futuristicTextFieldColors(true, DarkPrimary),
                        shape = RoundedCornerShape(18.dp)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        colors = futuristicTextFieldColors(true, DarkPrimary),
                        shape = RoundedCornerShape(18.dp)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
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
                            when {
                                name.isEmpty() || email.isEmpty() || userName.isEmpty() || password.isEmpty() -> {
                                    errorMessage = "Please fill in all fields"
                                }
                                else -> {
                                    val user = Users(
                                        username = userName,
                                        name = name,
                                        email = email,
                                        password = password,
                                        bio = bio,
                                        profileImageUri = null
                                    )
                                    userViewModel.registerUser(user) { success ->
                                        if (success) {
                                            navController.navigate("loginPage")
                                        } else {
                                            errorMessage = "Username or email already exists"
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(bottom = 16.dp),
                        colors = futuristicButtonColors(DarkPrimary),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = "Create Account",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account? ",
                            color = Color(0xFFB0B0C0),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Login",
                            modifier = Modifier.clickable {
                                navController.navigate("loginPage")
                            },
                            color = DarkPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun PrikazRegister() {
    //RegisterPage(navController = rememberNavController())
}