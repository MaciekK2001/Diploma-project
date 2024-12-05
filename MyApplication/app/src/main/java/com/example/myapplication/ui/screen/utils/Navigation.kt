package com.example.myapplication.ui.screen.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screen.LoginPage
import com.example.myapplication.ui.screen.RegistrationPage
import com.example.myapplication.ui.screen.Screen
import com.example.myapplication.ui.screen.UserScreen
import com.example.myapplication.ui.screen.UsersRankingScreen

@Composable
fun LoginNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route){
            LoginPage(navController = navController)
        }

        composable(route = Screen.Registration.route){
            RegistrationPage(navController = navController)
        }
    }
}

@Composable
fun MainNavigation(modifier: Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.UserScreen.route,  modifier = modifier) {
        composable(route = Screen.UserScreen.route) {
            UserScreen(navController = navController)
        }

        composable(route = Screen.UsersRankingScreen.route) {
            UsersRankingScreen(navController = navController)
        }
    }
}

