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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.myapplication.dtos.ChangePasswordDTO
import com.example.myapplication.network.networkResponses.ApiResponse
import com.example.myapplication.ui.viewModels.ChangePasswordViewModel

@Composable
fun ChangePasswordScreen(
    changePasswordViewModel: ChangePasswordViewModel = viewModel(),
    navController: NavController
) {
    val currentPassword = remember { mutableStateOf(TextFieldValue()) }
    val newPassword = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }

    val changeResponse by changePasswordViewModel.changeResponse.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Reset Password",
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Current Password") },
                value = currentPassword.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { currentPassword.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "New Password") },
                value = newPassword.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { newPassword.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Confirm New Password") },
                value = confirmPassword.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { confirmPassword.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        changePasswordViewModel.changePassword(ChangePasswordDTO(
                            currentPassword.value.text,
                            newPassword.value.text))
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Reset Password")
                }
            }

            if (changeResponse is ApiResponse.Error) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = (changeResponse as ApiResponse.Error).message,
                    color = Color.Red
                )
            }
        }

        LaunchedEffect(changeResponse is ApiResponse.Success) {
            if (changeResponse is ApiResponse.Success) {
                navController.navigate("user_screen?userId=${null}")
            }
        }
    }
}
