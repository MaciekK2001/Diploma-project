package com.example.myapplication.ui.screen

sealed class Screen (val route: String) {
    data object Login : Screen("login_screen")
    data object Registration : Screen("registration_screen")
    data object UserScreen : Screen("user_screen")
    data object UsersRankingScreen : Screen("users_ranking_screen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}