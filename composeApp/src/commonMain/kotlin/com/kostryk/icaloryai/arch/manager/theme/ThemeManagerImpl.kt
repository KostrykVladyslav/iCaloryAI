package com.kostryk.icaloryai.arch.manager.theme

import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import com.kostryk.icaloryai.domain.manager.settings.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeManagerImpl(private val settingsManager: SettingsManager) : ThemeManager {

    private val _themeFlow = MutableStateFlow(getThemeType())

    override fun observeThemeChange() = _themeFlow.asStateFlow()

    override fun setThemeType(themeType: ThemeType) {
        settingsManager.set(SettingsManager.SELECTED_THEME_KEY, themeType.toString())
        _themeFlow.value = themeType
    }

    override fun getThemeType() = ThemeType.fromString(
        value = settingsManager.get(
            key = SettingsManager.SELECTED_THEME_KEY,
            defaultValue = ThemeType.SYSTEM.toString()
        )
    )
}