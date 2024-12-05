package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.jwt.TokenManager
import com.example.myapplication.jwt.TokenManagerHolder
import com.example.myapplication.ui.screen.utils.LoginNavigation
import com.example.myapplication.ui.theme.MyApplicationTheme


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tokenManager = TokenManager.getInstance(this)
        TokenManagerHolder.initialize(tokenManager)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginNavigation()
                }
            }
        }
    }
}

