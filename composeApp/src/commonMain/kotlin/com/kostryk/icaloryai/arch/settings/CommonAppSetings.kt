package com.kostryk.icaloryai.arch.settings

import com.russhwolf.settings.ObservableSettings

private var settings: ObservableSettings? = null

internal fun getSettings(): ObservableSettings {
    if (settings == null) {
        settings = createLocalUserPref()
    }
    return settings ?: throw IllegalStateException("Settings not initialized")
}

internal object SettingsFields {

    const val SYSTEM_THEME_MODE = "system_theme_mode"
}