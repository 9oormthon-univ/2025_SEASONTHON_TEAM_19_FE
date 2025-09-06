package com.example.synergy.ui.component

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.synergy.R
import com.example.synergy.navigation.NavigationRoute

@Composable
fun SYNERGYNavigationBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavController,
) {
    val currentScreen = currentBackStackEntry?.destination?.route
    val isBottomBar =
        currentScreen !in listOf(NavigationRoute.SignUp.route, NavigationRoute.SignIn.route)

    val bottomItems = listOf(
        Pair(NavigationRoute.Home.route, stringResource(R.string.home)),
        Pair(NavigationRoute.Lecture.route, stringResource(R.string.lecture)),
        Pair(NavigationRoute.User.route, stringResource(R.string.user))
    )
    var selectedItem by remember { mutableStateOf(bottomItems.first().first) }

    when (currentScreen) {
        NavigationRoute.Home.route -> selectedItem = NavigationRoute.Home.route
        NavigationRoute.Lecture.route -> selectedItem = NavigationRoute.Lecture.route
        NavigationRoute.User.route -> selectedItem = NavigationRoute.User.route
    }

    if (isBottomBar) {
        NavigationBar {
            bottomItems.forEach { item ->
                val (route, label) = item
                NavigationBarItem(
                    icon = {},
                    label = { Text(text = label) },
                    selected = selectedItem == label,
                    onClick = {
                        navController.navigate(route) {
                            selectedItem = label
                            popUpTo(NavigationRoute.Home.route) {
                                inclusive = true
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}