package com.example.myapplication.ui.screen

sealed class Screen (val route: String) {
    object Login : Screen("login_screen")
    object Registration : Screen("registration_screen")
    object UserScreen : Screen("user_screen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}