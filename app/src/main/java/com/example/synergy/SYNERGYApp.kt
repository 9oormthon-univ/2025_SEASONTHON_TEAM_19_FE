package com.example.synergy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.synergy.navigation.NavigationRoute
import com.example.synergy.ui.home.HomeScreen
import com.example.synergy.ui.signin.SignInScreen
import com.example.synergy.ui.signup.SignUpScreen

@Composable
fun SYNERGYApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {},
        bottomBar = {}
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.SignUp.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NavigationRoute.SignUp.route) {
                SignUpScreen {
                    navController.navigate(NavigationRoute.SignIn.route)
                }
            }
            composable(route = NavigationRoute.SignIn.route) {
                SignInScreen {
                    navController.navigate(NavigationRoute.Home.route)
                }
            }
            composable(route = NavigationRoute.Home.route) {
                HomeScreen()
            }
        }
    }
}