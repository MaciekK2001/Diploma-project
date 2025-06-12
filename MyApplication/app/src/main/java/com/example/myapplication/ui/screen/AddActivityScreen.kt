package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.rememberPickerState
import com.example.myapplication.dtos.ActivityCreateDTO
import com.example.myapplication.model.ActivityType
import com.example.myapplication.network.networkResponses.ApiResponse
import com.example.myapplication.ui.viewModels.AddActivitiesViewModel
import java.util.concurrent.TimeUnit

@Composable
fun AddActivityScreen(
    activityId: String?, burntCalories: Int?, time: Long?, activityType: ActivityType?,
    viewModel: AddActivitiesViewModel = viewModel(),
    navController: NavController
) {

    val burntCaloriesSelected = remember { mutableStateOf(TextFieldValue()) }
    val hoursSelected = remember { mutableIntStateOf(0) }
    val minutesSelected = remember { mutableIntStateOf(0) }
    val secondsSelected = remember { mutableIntStateOf(0) }
    val activityTypeSelected = remember { mutableStateOf("OTHER") }
    val apiResponse by viewModel.apiResponse.collectAsState()

    LaunchedEffect(Unit) {
        setParameters(
            time, burntCalories, activityType, hoursSelected,
            minutesSelected, secondsSelected, burntCaloriesSelected, activityTypeSelected
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                label = { Text(text = "burntCalories") },
                value = burntCaloriesSelected.value,
                onValueChange = { burntCaloriesSelected.value = it })
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StyledNumberPicker((0..59).toList(), "Hours") { value ->
                    hoursSelected.intValue = value
                }
                StyledNumberPicker(
                    (0..59).toList(),
                    "Minutes"
                ) { value -> minutesSelected.intValue = value }
                StyledNumberPicker(
                    (0..59).toList(),
                    "Seconds"
                ) { value -> secondsSelected.intValue = value }
            }
            Spacer(modifier = Modifier.padding(20.dp))
            RadioButtonActivityType(activityTypeSelected)
            Spacer(modifier = Modifier.padding(20.dp))
            if (activityId == null) {
                Button(onClick = {
                    viewModel.addActivity(
                        ActivityCreateDTO(
                            burntCaloriesSelected.value.text.toInt(),
                            ((secondsSelected.intValue + minutesSelected.intValue * 60 + hoursSelected.intValue * 3600) * 1000).toLong(),
                            ActivityType.valueOf(activityTypeSelected.value)
                        )
                    )
                }) { Text("Add") }
            } else {
                Button(onClick = {
                    viewModel.updateActivity(
                        ActivityCreateDTO(
                            burntCaloriesSelected.value.text.toInt(),
                            ((secondsSelected.intValue + minutesSelected.intValue * 60 + hoursSelected.intValue * 3600) * 1000).toLong(),
                            ActivityType.valueOf(activityTypeSelected.value)
                        ), activityId
                    )
                }) { Text("Update") }
            }

            if (apiResponse != null) {
                if (apiResponse is ApiResponse.Error) {
                    Text(
                        text = (apiResponse as ApiResponse.Error).message,
                        color = Color.Red
                    )
                } else {
                    LaunchedEffect(apiResponse) {
                        navController.navigate("activities_screen?userid=${null}")
                    }
                }
            }
        }
    }

}

@Composable
fun StyledNumberPicker(items: List<Int>, timeType: String, onValueSelected: (Int) -> Unit) {
    val state = rememberPickerState(items.size)
    val contentDescription by remember { derivedStateOf { "${state.selectedOption}" } }

    val selectedValue = remember { mutableStateOf(items[state.selectedOption]) }
    LaunchedEffect(state.selectedOption) {
        selectedValue.value = items[state.selectedOption]
        onValueSelected(selectedValue.value)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(120.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            text = "${timeType}:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Picker(
            modifier = Modifier
                .width(80.dp)
                .height(150.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(12.dp)),
            state = state,
            contentDescription = contentDescription,
        ) { index ->
            Text(
                text = "${items[index]}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(
                        if (state.selectedOption == index) Color.LightGray else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

}

@Composable
fun RadioButtonActivityType(selectedOption: MutableState<String>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Activity Type:",
            style = MaterialTheme.typography.bodyLarge
        )
        listOf("WORKOUT", "RUN", "SWIM", "DANCE", "TEAM_SPORT", "OTHER").forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                RadioButton(
                    selected = selectedOption.value == option,
                    onClick = { selectedOption.value = option }
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

fun setParameters(
    pTime: Long?, pBurntCalories: Int?, pActivityType: ActivityType?,
    hours: MutableState<Int>, minutes: MutableState<Int>, seconds: MutableState<Int>,
    burntCalories: MutableState<TextFieldValue>, activityType: MutableState<String>
) {
    if (pTime != null) {
        hours.value = TimeUnit.MILLISECONDS.toHours(pTime).toInt()
        minutes.value = TimeUnit.MILLISECONDS.toMinutes(pTime).toInt() % 60
        seconds.value = TimeUnit.MILLISECONDS.toSeconds(pTime).toInt() % 60
    }
    if (pBurntCalories != null) {
        burntCalories.value = TextFieldValue(pBurntCalories.toString())
    }
    if (pActivityType != null) {
        activityType.value = pActivityType.toString()
    }
}

