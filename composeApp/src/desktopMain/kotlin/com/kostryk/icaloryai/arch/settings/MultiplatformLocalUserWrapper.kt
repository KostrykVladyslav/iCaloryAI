package com.kostryk.icaloryai.arch.settings

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual fun createLocalUserPref(): ObservableSettings {
    val preferences = Preferences.userRoot()
    return PreferencesSettings(preferences)
}