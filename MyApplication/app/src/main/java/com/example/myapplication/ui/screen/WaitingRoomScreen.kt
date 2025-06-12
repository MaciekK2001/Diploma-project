package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

data class UserDTO(val username: String)

@Composable
fun UserListWithActions(users: List<UserDTO>, onActionClick: (UserDTO, String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users) { user ->
            UserRowWithActions(user = user, onActionClick = onActionClick)
        }
    }
}

@Composable
fun UserRowWithActions(user: UserDTO, onActionClick: (UserDTO, String) -> Unit) {
    val randomDouble = Random.nextDouble()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.username,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if(randomDouble > 0.5) {
                Button(
                    onClick = { onActionClick(user, "send") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Send Request")
                }
            } else {
                Button(
                    onClick = { onActionClick(user, "accept") },
                    colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue, // Tło przycisku
                    contentColor = Color.White)
                ) {
                    Text(text = "Accept Match")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserListWithActions() {
    val sampleUsers = listOf(
        UserDTO("Alice"),
        UserDTO("Bob"),
        UserDTO("Charlie"),
        UserDTO("Diana")
    )

    UserListWithActions(
        users = sampleUsers,
        onActionClick = { user, action ->
            println("${action.capitalize()} action clicked for ${user.username}")
        }
    )
}
