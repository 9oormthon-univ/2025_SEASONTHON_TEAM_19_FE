package com.example.synergy.ui.component

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.synergy.R
import com.example.synergy.navigation.NavigationRoute
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color

@Composable
fun SYNERGYNavigationBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavController,
) {
    val currentScreen = currentBackStackEntry?.destination?.route
    val isBottomBar =
        currentScreen !in listOf(NavigationRoute.SignUp.route, NavigationRoute.SignIn.route)

    val bottomItems = listOf(
        Triple(NavigationRoute.Home.route, stringResource(R.string.home), R.drawable.ic_home),
        Triple(NavigationRoute.Lecture.route, stringResource(R.string.lecture), R.drawable.ic_lecture),
        Triple(NavigationRoute.Mentoring.route, stringResource(R.string.mentoring), R.drawable.ic_mentoring),
        Triple(NavigationRoute.User.route, stringResource(R.string.user), R.drawable.ic_user)
    )
    var selectedItem by remember { mutableStateOf(bottomItems.first().first) }

    when (currentScreen) {
        NavigationRoute.Home.route -> selectedItem = NavigationRoute.Home.route
        NavigationRoute.Lecture.route -> selectedItem = NavigationRoute.Lecture.route
        NavigationRoute.Mentoring.route -> selectedItem = NavigationRoute.Mentoring.route
        NavigationRoute.User.route -> selectedItem = NavigationRoute.User.route
    }

    if (isBottomBar) {
        NavigationBar {
            bottomItems.forEach { item ->
                val (route, label, iconRes) = item
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = label
                        )
                    },
                    label = { Text(text = label) },
                    selected = selectedItem == route,
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
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}