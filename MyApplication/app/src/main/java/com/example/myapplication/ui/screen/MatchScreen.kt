package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UserProgress(name: String, progress: Int, maxProgress: Int = 2000) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nazwa użytkownika
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Wynik użytkownika
        Text(
            text = "$progress m",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Pasek postępu
        LinearProgressIndicator(
            progress = progress.toFloat() / maxProgress,
            modifier = Modifier
                .fillMaxWidth(0.8f) // Pasek zajmuje 80% szerokości
                .height(10.dp)
                .clip(MaterialTheme.shapes.medium), // Zaokrąglone rogi
            color = Color(0xFF6200EE), // Kolor wypełnienia (fioletowy)
            trackColor = Color.LightGray // Kolor tła
        )
    }
}

@Composable
@Preview
fun UsersProgressPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Godzina startu
        Text(
            text = "Start Time: 14:30:27",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Progres użytkowników
        UserProgress(name = "You", progress = 2000) // You osiągnął 2000 metrów
        UserProgress(name = "Charlie", progress = 1950)

        // Napis "You Won!" i wynik
        Text(
            text = "You Won!",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Przycisk OK
        Button(
            onClick = { /* Działanie po kliknięciu OK */ },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(
                text = "OK",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}
