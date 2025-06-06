package com.kostryk.icaloryai.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kostryk.icaloryai.ui.details.DishDetailsScreen
import com.kostryk.icaloryai.ui.main.MainScreen
import com.kostryk.icaloryai.ui.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = NavigationRoute.Main.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(200)
            )
        },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(200)
            )
        },
    ) {
        composable(NavigationRoute.Main.route) { MainScreen(navController) }
        composable(
            route = NavigationRoute.DishDetails.route,
            arguments = listOf(navArgument(NavigationRoute.DishDetails.ARG_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val dishId = backStackEntry.arguments?.getString(NavigationRoute.DishDetails.ARG_ID)
            DishDetailsScreen(navController, dishId = dishId.orEmpty())
        }
        composable(NavigationRoute.Profile.route) { ProfileScreen(navController) }
    }
}

@Serializable
sealed class NavigationRoute(val route: String) {

    @Serializable
    object Main : NavigationRoute("main")

    @Serializable
    object DishDetails : NavigationRoute("dish_details/{id}") {
        const val ARG_ID = "id"
        fun createRoute(dishId: String) = "dish_details/$dishId"
    }

    @Serializable
    object Profile : NavigationRoute("profile")
}