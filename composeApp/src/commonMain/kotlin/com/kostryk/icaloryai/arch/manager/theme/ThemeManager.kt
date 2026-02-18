package com.kostryk.icaloryai.arch.manager.theme

import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import com.kostryk.icaloryai.domain.manager.Manager
import kotlinx.coroutines.flow.StateFlow

interface ThemeManager : Manager {

    fun setThemeType(themeType: ThemeType)

    fun observeThemeChange(): StateFlow<ThemeType>

    fun getThemeType(): ThemeType
}