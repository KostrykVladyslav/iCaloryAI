package com.kostryk.icaloryai.graph

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
data object MainRoute : NavKey

@Serializable
data class DishDetailsRoute(val dishId: String) : NavKey

@Serializable
data object ProfileRoute : NavKey

val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MainRoute::class, MainRoute.serializer())
            subclass(DishDetailsRoute::class, DishDetailsRoute.serializer())
            subclass(ProfileRoute::class, ProfileRoute.serializer())
        }
    }
}