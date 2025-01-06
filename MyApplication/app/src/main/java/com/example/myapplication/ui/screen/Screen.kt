package com.example.myapplication.ui.screen

sealed class Screen (val route: String) {
    data object Login : Screen("login_screen")
    data object Registration : Screen("registration_screen")
    data object UserScreen : Screen("user_screen")
    data object UsersRankingScreen : Screen("users_ranking_screen")
    data object ActivitiesScreen : Screen("activities_screen/{userId?}") {
        fun createRoute(userId: String? = null): String {
            return if (userId != null) "activities_screen/$userId" else "activities_screen"
        }
    }
    data object AddActivityScreen : Screen("add_activity_screen?activityId={activityId}&burntCalories={burntCalories}&time={time}&activityType={activityType}")
}
