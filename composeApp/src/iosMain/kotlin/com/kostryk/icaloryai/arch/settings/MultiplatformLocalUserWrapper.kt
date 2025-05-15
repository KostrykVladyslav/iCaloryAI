package com.kostryk.icaloryai.arch.settings

import com.russhwolf.settings.ObservableSettings

actual class MultiplatformLocalUserWrapper {

    actual fun createLocalUserPref(): ObservableSettings {
        val userDefaults = NSUserDefaults.standardUserDefaults
        return AppleSettings(userDefaults)
    }
}