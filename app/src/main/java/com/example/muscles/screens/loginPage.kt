package com.example.muscles.screens
import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.muscles.RoomDb.Users


@Composable
fun loginPage(navController: NavController,userViewModel: UserViewModel): Unit {


    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
    )

var userName by remember { mutableStateOf("") }

var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }


Card (modifier = Modifier.fillMaxSize(),
    colors = CardDefaults.cardColors(
        containerColor = Color(0xFF2A3D45)
    )){

    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top){

        Text(text= "Muscles",
            modifier = Modifier.padding(top = 80.dp, bottom = 30.dp), color = Color(0xffFFFFFF),
            fontSize = 60.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Text(text="You can never know enough about the human body",
          modifier = Modifier.padding(30.dp), color = Color(0xFF949494) ,
            fontSize = 20.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Thin,
            fontFamily = FontFamily.Serif, textAlign = TextAlign.Center

        )

        TextField(modifier = Modifier.padding(top = 45.dp, bottom = 20.dp),
            value = userName,
            onValueChange = {userName= it},
            label = {Text("Username:", modifier = Modifier
                .width(300.dp)
                .height(20.dp),

            )}
        )


        TextField(modifier = Modifier.padding(top = 45.dp, bottom = 20.dp),
            value = password,
            onValueChange = {password= it},
            label = {Text("Password:", modifier = Modifier
                .width(300.dp)
                .height(20.dp),
                ) },
            visualTransformation = PasswordVisualTransformation()

        )
        if(errorMessage.isNotEmpty())
        {
            Text(
            text=errorMessage,
            color=Color.Red,
                modifier = Modifier.padding(6.dp)
            )
        }

        Button(
            onClick = {
                if (userName.isNotEmpty() && password.isNotEmpty()) {
                    userViewModel.loginUser(userName, password) { user ->
                        if (user != null) {
                            navController.navigate("HomePage/$userName")
                        } else {
                            errorMessage = "Invalid username or password!"
                        }
                    }
                } else {
                    errorMessage = "All fields must be filled!"
                }
            },


            modifier = Modifier
                .padding(top = 40.dp)
                .width(240.dp)
                .height(50.dp),
            colors =ButtonDefaults.buttonColors(containerColor = Color(0xFF949494)),
            shape = RoundedCornerShape(20.dp),


        ) {
            Text(text= "Login", modifier = Modifier.padding(6.dp),
                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic


                )




        }
        Text(
            text= "Dont have an account? Register here ",
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    navController.navigate("registerPage")
                },
            color = Color.White,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center


        )


}//column

}
}//card


