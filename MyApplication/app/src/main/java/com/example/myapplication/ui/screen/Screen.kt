package com.example.myapplication.ui.screen

sealed class Screen (val route: String) {
    data object Login : Screen("login_screen")
    data object Registration : Screen("registration_screen")
    data object UserScreen : Screen("user_screen?username={username}")
    data object UsersRankingScreen : Screen("users_ranking_screen")
    data object ActivitiesScreen : Screen("activities_screen?userId={userId}")
    data object AddActivityScreen : Screen("add_activity_screen?activityId={activityId}&burntCalories={burntCalories}&time={time}&activityType={activityType}")
    data object ChangePasswordScreen : Screen("change_password_screen")
    data object StatisticsScreen : Screen("statistics_screen")
    data object MatchScreen : Screen("match_screen")
}
