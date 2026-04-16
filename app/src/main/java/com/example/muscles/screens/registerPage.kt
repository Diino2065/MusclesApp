package com.example.muscles.screens

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


@Composable
fun RegisterPage(navController: NavController, userViewModel: UserViewModel) {

    var userName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val bio by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A3D45))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
                modifier = Modifier.padding(top = 80.dp, bottom = 30.dp), color = Color(0xffFFFFFF),
                fontSize = 40.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "Welcome to register page. Please enter your credentials",
                modifier = Modifier.padding(30.dp), color = Color(0xFF949494),
                fontSize = 20.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Thin,
                fontFamily = FontFamily.Serif, textAlign = TextAlign.Center

            )

            TextField(modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        "Name:", modifier = Modifier
                            .width(300.dp)
                            .height(20.dp),

                        )
                }
            )
            TextField(modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        "Email:", modifier = Modifier
                            .width(300.dp)
                            .height(20.dp),

                        )
                }
            )
            TextField(modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                value = userName,
                onValueChange = { userName = it },
                label = {
                    Text(
                        "Username:", modifier = Modifier
                            .width(300.dp)
                            .height(20.dp),

                        )
                }
            )

            TextField(modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text(
                        "Password:", modifier = Modifier
                            .width(300.dp)
                            .height(20.dp),

                        )
                }
            )
            if(errorMessage.isNotEmpty())
            {
                Text(
                    text=errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(6.dp)
                )
            }

            Button(
                onClick = {
                    val user = Users(username = userName, name = name, email = email, password = password, bio = bio, profileImageUri = null)
                    userViewModel.registerUser(user) { success ->
                        if (success) {
                            navController.navigate("loginPage")
                        } else {
                            errorMessage = "Registration failed. Username or email might already exist."
                        }
                    }



                },
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(240.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF949494)),
                shape = RoundedCornerShape(20.dp),


                ) {
                Text(
                    text = "Register", modifier = Modifier.padding(6.dp),
                    fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic


                )


            }
        }
    }
}

@Preview
@Composable
fun PrikazRegister() {
    //RegisterPage(navController = rememberNavController())
}