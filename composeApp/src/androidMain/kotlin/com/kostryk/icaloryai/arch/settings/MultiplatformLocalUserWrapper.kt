package com.kostryk.icaloryai.arch.settings

import android.content.Context
import com.kostryk.icaloryai.utils.ContextUtils
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

actual class MultiplatformLocalUserWrapper {

    val context = ContextUtils.context

    actual fun createLocalUserPref(): ObservableSettings {
        val sharedPref =
            context.getSharedPreferences("icaloryai_main_prefs", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(sharedPref)
    }
}