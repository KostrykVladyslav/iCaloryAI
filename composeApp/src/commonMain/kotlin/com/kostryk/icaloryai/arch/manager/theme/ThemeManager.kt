package com.kostryk.icaloryai.arch.manager.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import com.kostryk.icaloryai.domain.manager.Manager
import kotlinx.coroutines.flow.StateFlow

interface ThemeManager : Manager {

    fun setThemeType(themeType: ThemeType)

    fun observeThemeChange(): StateFlow<ThemeType>

    fun getThemeType(): ThemeType

    @Composable
    @ReadOnlyComposable
    fun isDarkTheme(): Boolean
}