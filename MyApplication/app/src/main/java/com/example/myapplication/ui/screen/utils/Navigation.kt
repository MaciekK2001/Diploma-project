package com.example.myapplication.ui.screen.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.model.ActivityType
import com.example.myapplication.ui.screen.ActivitiesScreen
import com.example.myapplication.ui.screen.AddActivityScreen
import com.example.myapplication.ui.screen.LoginPage
import com.example.myapplication.ui.screen.RegistrationPage
import com.example.myapplication.ui.screen.Screen
import com.example.myapplication.ui.screen.UserScreen
import com.example.myapplication.ui.screen.UsersRankingScreen


@Composable
fun LoginNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginPage(navController = navController)
        }

        composable(route = Screen.Registration.route) {
            RegistrationPage(navController = navController)
        }
    }
}

@Composable
fun MainNavigation(modifier: Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.UserScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.UserScreen.route) {
            UserScreen(navController = navController)
        }

        composable(route = Screen.UsersRankingScreen.route) {
            UsersRankingScreen(navController = navController)
        }

        composable(route = Screen.ActivitiesScreen.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ActivitiesScreen(navController = navController, userId = userId)
        }
        composable(
                route = "add_activity_screen?activityId={activityId}&burntCalories={burntCalories}&time={time}&activityType={activityType}",
            arguments = listOf(
                navArgument("activityId") { type = NavType.StringType; nullable = true },
                navArgument("burntCalories") { type = NavType.IntType; defaultValue = 0 },
                navArgument("time") { type = NavType.StringType; nullable = true },
                navArgument("activityType") { type = NavType.StringType; nullable = true }
            )) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId")
            val burntCalories = backStackEntry.arguments?.getInt("burntCalories") ?: 0
            val time = backStackEntry.arguments?.getLong("time") ?: System.currentTimeMillis()
            val activityType = backStackEntry.arguments?.getString("activityType")?.let {
                runCatching { ActivityType.valueOf(it) }.getOrNull()
            }

            AddActivityScreen(activityId, burntCalories, time, activityType, navController = navController)
        }
    }

}


