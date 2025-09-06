package com.example.synergy.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.synergy.R
import com.example.synergy.navigation.NavigationRoute

data class TopBarAction(
    val icon: Int,
    val contentDescription: String? = null,
    val onClick: () -> Unit,
)

@Composable
fun SYNERGYTopBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavController,
) {
    when (currentBackStackEntry?.destination?.route) {
        NavigationRoute.Home.route -> SYNERGYDefaultTopBar()
        NavigationRoute.Lecture.route -> SYNERGYDefaultTopBar(
            title = stringResource(R.string.lecture),
            actionIcons = listOf(
                TopBarAction(R.drawable.baseline_add_24, "add_button") {

                },
                TopBarAction(R.drawable.baseline_search_24, "search_button") {

                }
            ),
            onBackClick = { navController.popBackStack() }
        )
        NavigationRoute.Mentoring.route -> SYNERGYDefaultTopBar(
            title = stringResource(R.string.mentoring),
            actionIcons = listOf(
                TopBarAction(R.drawable.baseline_search_24, "search_button") {

                }
            )
        )
        NavigationRoute.MentorDetail.route -> SYNERGYDefaultTopBar(
            enableNavigationIcon = true,
            onBackClick = { navController.popBackStack() }
        )
        NavigationRoute.MentorApply.route -> SYNERGYDefaultTopBar(
            title = stringResource(R.string.apply_mentor),
            enableNavigationIcon = true,
            onBackClick = { navController.popBackStack() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SYNERGYDefaultTopBar(
    title: String? = null,
    actionIcons: List<TopBarAction>? = null,
    enableNavigationIcon: Boolean = false,
    onBackClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.W500
                )
            }
        },
        navigationIcon = {
            if (enableNavigationIcon) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "BackIconButton"
                    )
                }
            }
        },
        actions = {
            actionIcons?.let {
                it.forEach { action ->
                    Row {
                        IconButton(onClick = { action.onClick }) {
                            Icon(
                                painter = painterResource(action.icon),
                                contentDescription = action.contentDescription
                            )
                        }
                    }
                }
            }
        },
    )
}