package com.kostryk.icaloryai.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kostryk.icaloryai.domain.enums.theme.ThemeType

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Color.White,
    onSecondary = Color.White,
    surface = Color.DarkGray,
    surfaceVariant = Color.Gray,
    onSurface = Color.White,
    onSurfaceVariant = Color.LightGray
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    surface = Color.White,
    surfaceVariant = Color.LightGray,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Gray
)

@Composable
fun iCaloryAITheme(
    themeType: ThemeType = ThemeType.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDark = when (themeType) {
        ThemeType.DARK -> true
        ThemeType.LIGHT -> false
        ThemeType.SYSTEM -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}