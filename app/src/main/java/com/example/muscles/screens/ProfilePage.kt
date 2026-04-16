package com.example.muscles.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.muscles.RoomDb.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavController,
    userViewModel: UserViewModel,
    username: String
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<String?>(null) }
    var isSaving by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri?.toString()
    }

    LaunchedEffect(username) {
        if (username.isBlank()) return@LaunchedEffect
        userViewModel.getUserByUsername(username) { user ->
            if (user != null) {
                name = user.name
                email = user.email
                bio = user.bio.orEmpty()
                profileImageUri = user.profileImageUri
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A3D45))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 6.dp)
                        .clickable { }
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Muscles",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(20.dp)
                        .clickable { navController.navigate("HomePage") }
                )
            }

            if (!profileImageUri.isNullOrBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "ProfileImage",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(100.dp),
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "ProfileImage",
                    tint = Color.White
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable { imagePicker.launch("image/*") },
                text = "Edit profile image",
                color = Color.White,
                fontStyle = FontStyle.Normal,
                textDecoration = TextDecoration.Underline
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        modifier = Modifier.padding(start = 10.dp, top = 30.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline,
                        text = "Name:"
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        text = name
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline,
                        text = "Username:"
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        text = username
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline,
                        text = "Email:"
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        text = email
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline,
                        text = "Bio:"
                    )

                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .size(200.dp),
                        textStyle = TextStyle(color = Color.White, fontSize = 10.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Enter your bio ",
                                color = Color.Gray,
                                textDecoration = TextDecoration.None,
                                fontSize = 10.sp
                            )
                        }
                    )

                    Button(
                        onClick = {
                            if (username.isNotBlank()) {
                                userViewModel.updateUserProfile(
                                    username = username,
                                    name = name,
                                    email = email,
                                    bio = bio,
                                    profileImageUri = profileImageUri
                                ) { success ->
                                    if (success) {
                                        bio = bio
                                    }
                                }
                            }
                        },
                        enabled = !isSaving,
                        modifier = Modifier
                            .width(240.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF949494)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = if (isSaving) "Saving..." else "Save",
                            modifier = Modifier.padding(6.dp),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    }

                    Button(
                        onClick = { navController.navigate("HomePage") },
                        modifier = Modifier
                            .width(240.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF949494)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Close",
                            modifier = Modifier.padding(6.dp),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Text("Preview requires real UserViewModel and username from navigation.")
}
