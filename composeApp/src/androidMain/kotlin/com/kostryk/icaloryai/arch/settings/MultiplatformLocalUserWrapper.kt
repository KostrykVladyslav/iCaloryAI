package com.kostryk.icaloryai.arch.settings

import android.content.Context
import com.kostryk.icaloryai.utils.ContextUtils
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

internal actual fun createLocalUserPref(): ObservableSettings {
    val sharedPref =
        ContextUtils.context.getSharedPreferences("icaloryai_main_prefs", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(sharedPref)
}