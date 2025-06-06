package com.kostryk.icaloryai.graph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = NavigationRoute.Main.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it / 4 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it / 4 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
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