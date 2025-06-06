package com.kostryk.icaloryai.graph

import kotlinx.serialization.Serializable

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