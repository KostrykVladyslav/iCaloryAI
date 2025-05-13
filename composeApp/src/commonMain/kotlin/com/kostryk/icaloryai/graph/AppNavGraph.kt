package com.kostryk.icaloryai.graph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kostryk.icaloryai.ui.details.DishDetailsScreen
import com.kostryk.icaloryai.ui.main.MainScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = NavigationRoute.Main.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(NavigationRoute.Main.route) { MainScreen() }
        composable(
            route = NavigationRoute.DishDetails.route,
            arguments = listOf(navArgument(NavigationRoute.DishDetails.ARG_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val dishId = backStackEntry.arguments?.getString(NavigationRoute.DishDetails.ARG_ID)
            DishDetailsScreen(dishId = dishId.orEmpty())
        }
    }
}

sealed class NavigationRoute(val route: String) {

    object Main : NavigationRoute("main")

    object DishDetails : NavigationRoute("dish_details/{id}") {
        const val ARG_ID = "id"
        fun createRoute(dishId: String) = "dish_details/$dishId"
    }

    object Settings : NavigationRoute("settings")
}