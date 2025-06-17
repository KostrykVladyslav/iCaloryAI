package com.kostryk.icaloryai.domain.manager.settings

interface SettingsManager {

    fun <T> get(key: String, defaultValue: T): T

    fun <T> set(key: String, value: T)

    companion object {
        const val USER_GENDER_KEY = "user_gender"
        const val USER_AGE_KEY = "user_age"
        const val USER_HEIGHT_KEY = "user_height"
        const val USER_CURRENT_WEIGHT_KEY = "user_current_weight"
        const val DAILY_CALORIE_INTAKE_KEY = "user_calorie_intake"
        const val DAILY_PROTEIN_INTAKE_KEY = "user_protein_intake"
        const val DAILY_FAT_INTAKE_KEY = "user_fat_intake"
        const val DAILY_CARBS_INTAKE_KEY = "user_carbs_intake"
        const val SELECTED_THEME_KEY = "selected_theme"
    }
}