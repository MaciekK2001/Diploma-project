package com.example.myapplication.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.viewModels.UserScreenViewModel

@Composable
fun UserScreen(navController: NavController?,
               userScreenViewModel: UserScreenViewModel = viewModel()) {

    val userStatsGetDTO by userScreenViewModel.userStatsGetDTO.collectAsState()
    val isUserLoaded by userScreenViewModel.isUserLoaded.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (!isUserLoaded){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        else {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row {
                    Text(
                        text = "User Info",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp),
                    )

                    Text(
                        text = "${userStatsGetDTO.appUser?.email ?: "Unknown"}",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 24.dp)
                            .offset(x = 8.dp),
                    )
                }

                Text(
                    text = "${userStatsGetDTO.appUser?.firstName ?: "Unknown"} " +
                            "${userStatsGetDTO.appUser?.lastName ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )


                Text(
                    text = "Joined at ${userStatsGetDTO.appUser?.joinedAt ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Favourite activity: ${userStatsGetDTO.favActivityType ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "${userStatsGetDTO.appUser?.about ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Last activity",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp),
                )

                Text(
                    text = "${userStatsGetDTO.lastActivity?.activityType ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Duration: ${userStatsGetDTO.lastActivity?.time ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Burnt calories: ${userStatsGetDTO.lastActivity?.burntCalories ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

    }
}

@Composable
@Preview
fun preview(){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row (){
                    Text(
                        text = "User Info",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "maciek-kwiatkowski@o2.pl",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 24.dp)
                            .offset(x = 8.dp),
                    )
                }

                Text(
                    text = "Maciej Kwiatkowski",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )


                Text(
                    text = "Joined at 06.10.2022",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Favourite activity: FOOTBALL",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "About....",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Last activity",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp),
                )

                Text(
                    text = "SWIM",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Duration: 01:32:46",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Burnt calories: 1254 kcal",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {

                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Add activity")
                    }
                }

            }

    }
}