package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        items(listOfUsers){ item: UserRankingDTO ->
            Text(text = "${item.appUserBasicDataDTO.email}" + " ${item.burntCalories}")
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