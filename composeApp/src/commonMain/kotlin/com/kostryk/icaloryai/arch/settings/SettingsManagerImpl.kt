package com.kostryk.icaloryai.arch.settings

import com.kostryk.icaloryai.domain.manager.settings.SettingsManager

class SettingsManagerImpl : SettingsManager {

    private val settings = createLocalUserPref()

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> settings.getString(key, defaultValue) as T
            is Int -> settings.getInt(key, defaultValue) as T
            is Boolean -> settings.getBoolean(key, defaultValue) as T
            is Float -> settings.getFloat(key, defaultValue) as T
            is Long -> settings.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported type for key: $key")
        }
    }

    override fun <T> set(key: String, value: T) {
        when (value) {
            is String -> settings.putString(key, value)
            is Int -> settings.putInt(key, value)
            is Boolean -> settings.putBoolean(key, value)
            is Float -> settings.putFloat(key, value)
            is Long -> settings.putLong(key, value)
            else -> throw IllegalArgumentException("Unsupported type for key: $key")
        }
    }
}