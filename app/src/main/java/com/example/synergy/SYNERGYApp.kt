package com.example.synergy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.synergy.navigation.NavigationRoute
import com.example.synergy.ui.component.SYNERGYNavigationBar
import com.example.synergy.ui.component.SYNERGYTopBar
import com.example.synergy.ui.home.HomeScreen
import com.example.synergy.ui.lecture.LectureScreen
import com.example.synergy.ui.lecturelist.LectureListScreen
import com.example.synergy.ui.mentordetail.MentorDetailScreen
import com.example.synergy.ui.mentorlist.MentorListScreen
import com.example.synergy.ui.signin.SignInScreen
import com.example.synergy.ui.signup.SignUpScreen
import com.example.synergy.ui.user.UserScreen

@Composable
fun SYNERGYApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = { SYNERGYTopBar(currentBackStackEntry, navController) },
        bottomBar = { SYNERGYNavigationBar(currentBackStackEntry, navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.SignIn.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NavigationRoute.SignIn.route) {
                SignInScreen(
                    onSignUpClick = { navController.navigate(NavigationRoute.SignUp.route) },
                    onHomeClick = { navController.navigate(NavigationRoute.Home.route) }
                )
            }
            composable(route = NavigationRoute.SignUp.route) {
                SignUpScreen { navController.navigate(NavigationRoute.SignIn.route) }
            }
            composable(route = NavigationRoute.Home.route) {
                HomeScreen()
            }
            composable(route = NavigationRoute.Lecture.route) {
                LectureListScreen()
            }
            composable(route = NavigationRoute.Mentoring.route) {
                MentorListScreen(
                    onMentorClick = { id ->
                        navController.navigate(NavigationRoute.MentorDetail.routeWithId(id))
                    }
                )
            }
            composable(route = NavigationRoute.User.route) {
                UserScreen()
            }

            composable(
                route = NavigationRoute.MentorDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                MentorDetailScreen(mentorId = id)
            }
        }
    }
}