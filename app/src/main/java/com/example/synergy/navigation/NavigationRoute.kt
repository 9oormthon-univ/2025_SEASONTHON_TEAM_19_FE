package com.example.synergy.navigation

sealed class NavigationRoute(val route: String) {
    data object SignUp: NavigationRoute("sign_up")
    data object SignIn: NavigationRoute("sign_in")
    data object Home: NavigationRoute("home")
    data object Lecture: NavigationRoute("lecture")
    data object User: NavigationRoute("user")
}