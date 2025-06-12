package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.dtos.UserRankingDTO
import com.example.myapplication.ui.viewModels.UsersRankingViewModel

@Composable
fun UsersRankingScreen(navController: NavController?, viewModel: UsersRankingViewModel = viewModel()) {
    val listOfUsers by viewModel.listOfUsers.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNextPage()
    }

    LazyColumn {
        item{
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Show search parameters"
                    )
                }
            }
        }
        item {
            ColumnHeaders()
        }

        itemsIndexed(listOfUsers) { index, item ->
            UserDataRow(userData = item, position = index + 1, navController = navController)
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center),
                    onClick = { viewModel.loadNextPage() }
                ) {
                    Text(text = "Load More")
                }
            }
        }
    }
}

@Composable
fun ColumnHeaders() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Text(
            text = "Rank",
            Modifier
                .weight(0.5f)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = "Username",
            Modifier
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = "Burnt Calories",
            Modifier
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun UserDataRow(userData: UserRankingDTO, position: Int, navController: NavController?) {
    val lightPurple = Color.hsv(270f, 0.2f, 0.9f)
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController?.navigate("user_screen?username=${userData.appUserBasicDataDTO.username}")
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "$position",
                Modifier
                    .weight(0.5f)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "${userData.appUserBasicDataDTO.username}",
                Modifier
                    .weight(1f)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "${userData.burntCalories}",
                Modifier
                    .weight(1f)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
