package com.kostryk.icaloryai.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.kostryk.icaloryai.ui.details.DishDetailsScreen
import com.kostryk.icaloryai.ui.main.MainScreen
import com.kostryk.icaloryai.ui.profile.ProfileScreen

@Composable
fun AppNavGraph(modifier: Modifier) {
    val backStack = rememberNavBackStack(navConfig, MainRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<MainRoute> {
                MainScreen(
                    onNavigateToProfile = { backStack.add(ProfileRoute) },
                    onNavigateToDish = { dishId -> backStack.add(DishDetailsRoute(dishId.toString())) }
                )
            }
            entry<DishDetailsRoute> { key ->
                DishDetailsScreen(
                    dishId = key.dishId,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<ProfileRoute> {
                ProfileScreen(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        },
        modifier = modifier
    )
}