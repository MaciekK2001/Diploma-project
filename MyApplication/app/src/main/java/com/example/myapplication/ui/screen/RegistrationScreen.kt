package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.network.networkRequests.RegistrationRequest
import com.example.myapplication.network.networkResponses.ApiResponse
import com.example.myapplication.ui.viewModels.RegistrationViewModel

@Composable
fun RegistrationPage(navController: NavController, viewModel: RegistrationViewModel = viewModel()) {
    val registerResponse by viewModel.registerResponse.collectAsState()

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val firstName = remember { mutableStateOf(TextFieldValue()) }
            val lastName = remember { mutableStateOf(TextFieldValue()) }
            val email = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }
            val username = remember { mutableStateOf(TextFieldValue()) }

            Text(
                text = "Registration",
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "First name") },
                value = firstName.value,
                onValueChange = { firstName.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Last name") },
                value = lastName.value,
                onValueChange = { lastName.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Email") },
                value = email.value,
                onValueChange = { email.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "username") },
                value = username.value,
                onValueChange = { username.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Password") },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })


            Spacer(modifier = Modifier.height(20.dp))
            if (registerResponse is ApiResponse.Error) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = (registerResponse as ApiResponse.Error).message,
                    color = Color.Red
                )
            }
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        viewModel.register(RegistrationRequest(
                            firstName.value.text,
                            lastName.value.text,
                            username.value.text,
                            password.value.text,
                            email.value.text,
                            "USER",
                        ))
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Register")
                }
            }
        }
    }
}