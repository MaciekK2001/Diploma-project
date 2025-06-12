package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.utils.TimeConverter
import com.example.myapplication.model.Activity
import com.example.myapplication.model.ActivityType
import com.example.myapplication.security.UserDataHolder
import com.example.myapplication.ui.viewModels.ActivitiesViewModel
import com.example.myapplication.ui.viewModels.ActivitiesViewModel.SearchParameters

@Composable
fun ActivitiesScreen(
    navController: NavController,
    viewModel: ActivitiesViewModel = viewModel(),
    userId: String?
) {
    val listOfActivities by viewModel.listOfActivities.collectAsState()
    var isSearchParametersVisible by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.setUserId(userId)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { isSearchParametersVisible = !isSearchParametersVisible },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = if (isSearchParametersVisible) "Hide search parameters" else "Show search parameters"
                    )
                }
                if(userId == null) {
                    AddActivityButton { navController.navigate("add_activity_screen?activityId=${null}&burntCalories=${null}&time=${null}&activityType=${null}") }
                }
            }

            if (isSearchParametersVisible) {
                SearchParametersSection(viewModel)
            }

            ColumnHeader()
            LazyColumn {
                items(listOfActivities) { item ->
                    ActivityRow(activity = item, navController, viewModel = viewModel,
                        userId = UserDataHolder.userData.getUserId().toString())
                }
                if (listOfActivities.isNotEmpty()) {
                    item {
                        LoadMoreButton(onClick = { viewModel.loadNextPage() })
                    }
                }
            }
        }

    }
}

@Composable
fun SearchParametersSection(
    viewModel: ActivitiesViewModel
) {
    val activityTypes = ActivityType.entries
    val sortDirections = listOf("ASC", "DESC")
    val sortByOptions = listOf("createdAt", "burntCalories", "time")

    val searchParameters = remember { mutableStateOf(SearchParameters()) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Sort Direction", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            sortDirections.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = searchParameters.value.sortDirection == option,
                        onCheckedChange = { isChecked ->
                            searchParameters.value = searchParameters.value.copy(
                                sortDirection = if (isChecked) option else null
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(option)
                }
            }

            Text("Sort By", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            sortByOptions.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = searchParameters.value.sortBy == option,
                        onCheckedChange = { isChecked ->
                            searchParameters.value = searchParameters.value.copy(
                                sortBy = if (isChecked) option else null
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(option)
                }
            }
        }

        Column(modifier = Modifier.padding(8.dp)) {
            Text("Activity Types", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            activityTypes.forEach { activityType ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = activityType in searchParameters.value.selectedActivityTypes,
                        onCheckedChange = { isChecked ->
                            val newTypes = if (isChecked) {
                                searchParameters.value.selectedActivityTypes + activityType
                            } else {
                                searchParameters.value.selectedActivityTypes - activityType
                            }
                            searchParameters.value = searchParameters.value.copy(
                                selectedActivityTypes = newTypes
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(activityType.name)
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                viewModel.updateSearchParameters(searchParameters.value)
                viewModel.searchClicked()
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Search")
        }
    }
}

@Composable
fun ActivityRow(activity: Activity, navController: NavController, viewModel: ActivitiesViewModel,
                userId: String) {
    val lightPurple = Color.hsv(270f, 0.2f, 0.9f)
    var isClicked by remember { mutableStateOf(false) }
    var isdeleted by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(lightPurple, RoundedCornerShape(8.dp))
            .clickable { isClicked = !isClicked },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (!isdeleted) {
            if (isClicked && userId == activity.user.userId.toString()) {
                Button(onClick = { navController.navigate("add_activity_screen?activityId=${activity.activityId}&burntCalories=${activity.burntCalories}&time=${activity.time}&activityType=${activity.activityType}") })
                { Text("Update", color = Color.White) }
                Button(onClick = {
                    viewModel.deleteActivity(activity.activityId.toString())
                    isdeleted = !isdeleted
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            } else {
                Text(
                    text = "${activity.activityType}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )

                Text(
                    text = "${activity.burntCalories} kcal",
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = TimeConverter.convertToHours(activity.time)?: "",
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = TimeConverter.formatDate(activity.createdAt)?: "",
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
}


@Composable
fun LoadMoreButton(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp),
            onClick = onClick
        ) {
            Text("Load More")
        }
    }
}

@Composable
fun ColumnHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Activity Type",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Text(
            text = "Calories Burnt",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Text(
            text = "Time",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Text(
            text = "Created At",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }

}

@Composable
fun AddActivityButton(onClick: () -> Unit) {
    Box() {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
        ) {
            Icon(Icons.Filled.Add, "Add activity button")
        }
    }
}
