package com.kostryk.icaloryai.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import org.koin.compose.koinInject

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Color.White,
    onSecondary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.Black,
    onSecondary = Color.Black
)

@Composable
fun isDarkTheme(): Boolean {
    val themeManager = koinInject<ThemeManager>()
    return themeManager.isDarkTheme()
}

@Composable
fun iCaloryAITheme(
    isDarkTheme: Boolean = isDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}