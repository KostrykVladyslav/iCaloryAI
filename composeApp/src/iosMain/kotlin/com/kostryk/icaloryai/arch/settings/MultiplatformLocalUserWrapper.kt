package com.kostryk.icaloryai.arch.settings

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import platform.Foundation.NSUserDefaults

internal actual fun createLocalUserPref(): ObservableSettings {
    val userDefaults = NSUserDefaults.standardUserDefaults
    return NSUserDefaultsSettings(userDefaults)
}