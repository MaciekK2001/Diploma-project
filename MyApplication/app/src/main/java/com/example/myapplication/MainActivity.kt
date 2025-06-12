package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.security.TokenManager
import com.example.myapplication.security.TokenManagerHolder
import com.example.myapplication.security.UserData
import com.example.myapplication.security.UserDataHolder
import com.example.myapplication.ui.screen.utils.NavigationDrawer
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val token = intent.getStringExtra("TOKEN")
        val tokenManager = TokenManager.getInstance(this)
        val userData = UserData.getInstance(this)
        UserDataHolder.initialize(userData)
        TokenManagerHolder.initialize(tokenManager)
        if (token != null) {
            Log.d("MainActivity", "Received token: $token")
            TokenManagerHolder.tokenManager.saveJwtToken(token)
        }
        lifecycleScope.launch {
            userData.getData()
        }

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    NavigationDrawer(scope, drawerState, navController, context = LocalContext.current)
                }
            }
        }
    }

}