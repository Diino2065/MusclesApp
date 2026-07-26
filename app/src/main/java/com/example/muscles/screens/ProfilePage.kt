package com.example.muscles.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.muscles.RoomDb.UserViewModel
import com.example.muscles.ui.theme.DarkBackground
import com.example.muscles.ui.theme.DarkOnBackground
import com.example.muscles.ui.theme.DarkPrimary
import com.example.muscles.ui.theme.DarkSurface
import com.example.muscles.ui.theme.DarkTertiary
import com.example.muscles.ui.theme.LightBackground
import com.example.muscles.ui.theme.LightOnBackground
import com.example.muscles.ui.theme.LightPrimary
import com.example.muscles.ui.theme.LightSurface
import com.example.muscles.ui.theme.futuristicBackgroundBrush
import com.example.muscles.ui.theme.futuristicButtonColors
import com.example.muscles.ui.theme.futuristicCardColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavController,
    userViewModel: UserViewModel,
    username: String,
    isDarkMode: Boolean = true
) {
    var name by remember(username) { mutableStateOf("") }
    var email by remember(username) { mutableStateOf("") }
    var bioInput by remember(username) { mutableStateOf("") }
    var profileImageUri by remember(username) { mutableStateOf<String?>(null) }
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
                bioInput = user.bio.orEmpty()
                profileImageUri = user.profileImageUri
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(futuristicBackgroundBrush(isDarkMode)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 12.dp)
        ) {
            var menuExpanded by remember { mutableStateOf(false) }

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = if (isDarkMode) DarkOnBackground else LightOnBackground,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { menuExpanded = true }
            )

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Home Page") },
                    onClick = {
                        menuExpanded = false
                        navController.navigate("HomePage/$username")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Profile Page") },
                    onClick = {
                        menuExpanded = false
                        navController.navigate("ProfilePage/$username")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Exercise") },
                    onClick = {
                        menuExpanded = false
                        navController.navigate("Exercise/$username")
                    }
                )
            }

            androidx.compose.foundation.layout.Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Muscles",
                    color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.5.sp
                )
            }

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Exit",
                tint = if (isDarkMode) DarkOnBackground else LightOnBackground,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { navController.navigate("HomePage/$username") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = 20.dp)
        ) {
            item {
                Text(
                    text = "Profile",
                    modifier = Modifier.padding(start = 30.dp, top = 20.dp),
                    color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!profileImageUri.isNullOrBlank()) {
                        Image(
                            painter = rememberAsyncImagePainter(profileImageUri),
                            contentDescription = "ProfileImage",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(100.dp),
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "ProfileImage",
                            tint = if (isDarkMode) DarkPrimary else LightPrimary
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable { imagePicker.launch("image/*") },
                        text = "Edit profile image",
                        color = if (isDarkMode) DarkPrimary else LightPrimary,
                        fontStyle = FontStyle.Normal,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = futuristicCardColors(isDarkMode),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Name",
                            fontSize = 12.sp,
                            color = if (isDarkMode) Color(0xFF93C5FD) else Color(0xFF1F1F1F),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (name.isBlank()) "-" else name,
                            fontSize = 16.sp,
                            color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Text(
                            text = "Username",
                            fontSize = 12.sp,
                            color = if (isDarkMode) Color(0xFF93C5FD) else Color(0xFF1F1F1F),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = username,
                            fontSize = 16.sp,
                            color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Text(
                            text = "Email",
                            fontSize = 12.sp,
                            color = if (isDarkMode) Color(0xFF93C5FD) else Color(0xFF1F1F1F),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (email.isBlank()) "-" else email,
                            fontSize = 16.sp,
                            color = if (isDarkMode) DarkOnBackground else LightOnBackground,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Text(
                            text = "Bio",
                            fontSize = 12.sp,
                            color = if (isDarkMode) Color(0xFF93C5FD) else Color(0xFF1F1F1F),
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = bioInput,
                            onValueChange = { bioInput = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(top = 8.dp),
                            textStyle = TextStyle(
                                color = if (isDarkMode) Color.Black else Color(0xFF0F1419),
                                fontSize = 14.sp
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isDarkMode) DarkPrimary else LightPrimary,
                                unfocusedBorderColor = if (isDarkMode) Color.Gray else Color(0xFFD0D0D0),
                                focusedTextColor = if (isDarkMode) DarkOnBackground else LightOnBackground,
                                unfocusedTextColor = if (isDarkMode) DarkOnBackground else LightOnBackground,
                                cursorColor = if (isDarkMode) DarkPrimary else LightPrimary
                            ),
                            placeholder = {
                                Text(
                                    text = "Enter your bio",
                                    color = if (isDarkMode) Color.Gray else Color(0xFF9CA3AF),
                                    fontSize = 14.sp
                                )
                            }
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            if (username.isNotBlank()) {
                                isSaving = true
                                userViewModel.updateUserProfile(
                                    username = username,
                                    name = name.trim(),
                                    email = email.trim(),
                                    bio = bioInput.trim(),
                                    profileImageUri = profileImageUri
                                ) { saved ->
                                    if (saved) {
                                        userViewModel.getUserByUsername(username) { updatedUser ->
                                            if (updatedUser != null) {
                                                name = updatedUser.name
                                                email = updatedUser.email
                                                bioInput = updatedUser.bio.orEmpty()
                                                profileImageUri = updatedUser.profileImageUri
                                            }
                                            isSaving = false
                                        }
                                    } else {
                                        isSaving = false
                                    }
                                }
                            }
                        },
                        enabled = !isSaving,
                        modifier = Modifier
                            .width(240.dp)
                            .padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDarkMode) DarkPrimary else LightPrimary
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = if (isSaving) "Saving..." else "Save",
                            modifier = Modifier.padding(6.dp),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            color = Color.White
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .navigationBarsPadding()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { navController.navigate("Stats/$username") },
                        modifier = Modifier
                            .width(110.dp)
                            .padding(end = 5.dp),
                        colors = futuristicButtonColors(if (isDarkMode) DarkPrimary else LightPrimary),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text(
                            text = "Stats",
                            modifier = Modifier.padding(6.dp),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = { navController.navigate("HomePage/$username") },
                        modifier = Modifier
                            .width(110.dp)
                            .padding(start = 5.dp),
                        colors = futuristicButtonColors(if (isDarkMode) DarkTertiary else LightPrimary),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text(
                            text = "Close",
                            modifier = Modifier.padding(6.dp),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp,
                            color = Color.White
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

}
