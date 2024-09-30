package com.example.myapplication.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route){
            LoginPage(navController = navController)
        }

        composable(route = Screen.Registration.route){
            RegistrationPage(navController = navController)
        }
        
        composable(route = Screen.UserScreen.route){
            UserScreen(navController = navController)
        }


    }
}
