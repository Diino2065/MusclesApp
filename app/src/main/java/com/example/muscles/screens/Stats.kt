package com.example.muscles.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun Stats(navController: NavController){

    Card(
            modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A3D45))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier.padding(top=20.dp, start = 30.dp)
            )

            Text(

                text="Muscles",
                color = Color.White,
                modifier = Modifier.padding(start=90.dp,top=20.dp),
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Exit",
                tint = Color.White,
                modifier = Modifier.padding(top=20.dp, start = 70.dp, end = 10.dp).
                clickable {
                    navController.navigate("HomePage")
                }
            )


        }//row

        Column(
            modifier = Modifier,
        ) {
            Text(text="Stats",
                modifier = Modifier.padding(start = 30.dp,top=40.dp),
                color = Color.White,
                fontSize = 36.sp,
                fontStyle = FontStyle.Italic
            )
            StatsCard()
            yourFriends()

        }//column



    }//card






}

@Composable
fun StatsCard()
{

    var time: String by remember  { mutableStateOf("") }

    Card (
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ){
            Column (modifier = Modifier.padding(16.dp)){
                Text(text="Total time",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text=time,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text="+60% month over month",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green

                )

            }

    }

}

@Composable
fun yourFriends()
{

    Card(modifier = Modifier.fillMaxWidth().height(300.dp).
        padding(10.dp),
        elevation = CardDefaults.cardElevation(10.dp),


    )
    {
        Column {
            Text(text="Your friends", modifier = Modifier
                .padding(top = 20.dp,start=20.dp),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,

            )

            Row(modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Icon", modifier = Modifier.padding(start = 30.dp, top=30.dp).size(60.dp)

                )


                Text(text="Name", modifier = Modifier,
                    color = Color.Black,
                )
                Text(text="Mail", modifier = Modifier.padding(top = 30.dp),
                    color = Color.Black,

                )

                Spacer(modifier = Modifier.weight(0.6f))






            }//row
Row (modifier = Modifier,
    verticalAlignment = Alignment.CenterVertically){

    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "Icon", modifier = Modifier.padding(start = 30.dp, top=30.dp).size(60.dp)

    )
            Text(text="Name", modifier = Modifier,
                color = Color.Black,
            )
            Text(text="Mail", modifier = Modifier.padding(top = 30.dp),
                color = Color.Black,

                )
        }


        }

    }
}


@Preview//(showBackground = true)
@Composable
fun statsprikaz()
{
   Stats(navController = rememberNavController())
    //StatsCard()
}