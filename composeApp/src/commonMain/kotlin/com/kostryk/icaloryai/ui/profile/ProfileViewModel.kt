package com.kostryk.icaloryai.ui.profile

import com.kostryk.icaloryai.arch.manager.theme.ThemeManager
import com.kostryk.icaloryai.domain.enums.theme.ThemeType
import com.kostryk.icaloryai.domain.manager.settings.SettingsManager
import com.kostryk.icaloryai.lifecycle.BaseViewModel

class ProfileViewModel(
    private val settingsManager: SettingsManager,
    private val themeManager: ThemeManager
) : BaseViewModel() {

    fun getUserGender(): String {
        return settingsManager.get(SettingsManager.USER_GENDER_KEY, "")
    }

    fun getUserAge(): Int {
        return settingsManager.get(SettingsManager.USER_AGE_KEY, 0)
    }

    fun getUserHeight(): Int {
        return settingsManager.get(SettingsManager.USER_HEIGHT_KEY, 0)
    }

    fun getUserCurrentWeight(): Int {
        return settingsManager.get(SettingsManager.USER_CURRENT_WEIGHT_KEY, 0)
    }

    fun setUserGender(gender: String) {
        settingsManager.set(SettingsManager.USER_GENDER_KEY, gender)
    }

    fun setUserAge(age: Int) {
        settingsManager.set(SettingsManager.USER_AGE_KEY, age)
    }

    fun setUserHeight(height: Int) {
        settingsManager.set(SettingsManager.USER_HEIGHT_KEY, height)
    }

    fun setUserCurrentWeight(weight: Int) {
        settingsManager.set(SettingsManager.USER_CURRENT_WEIGHT_KEY, weight)
    }

    fun getThemeType() = themeManager.getThemeType()

    fun setThemeType(theme: ThemeType) = themeManager.setThemeType(theme)
}