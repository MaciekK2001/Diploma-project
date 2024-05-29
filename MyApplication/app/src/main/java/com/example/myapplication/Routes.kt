package com.example.myapplication

sealed class Routes(val route: String) {
    object Login : Routes("Login")
}