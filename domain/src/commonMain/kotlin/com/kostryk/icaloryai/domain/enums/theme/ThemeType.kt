package com.kostryk.icaloryai.domain.enums.theme

import kotlinx.serialization.Serializable

@Serializable
enum class ThemeType {

    SYSTEM,
    DARK,
    LIGHT;

    override fun toString() =
        when (this) {
            SYSTEM -> "system"
            DARK -> "dark"
            LIGHT -> "light"
        }

    companion object {
        fun fromString(value: String) = when (value.lowercase()) {
            "system" -> SYSTEM
            "dark" -> DARK
            "light" -> LIGHT
            else -> throw IllegalArgumentException("Unknown theme type: $value")
        }
    }
}