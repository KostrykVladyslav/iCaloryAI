package com.kostryk.icaloryai.arch.settings

import com.russhwolf.settings.ObservableSettings

expect class MultiplatformLocalUserWrapper {
    expect fun createLocalUserPref(): ObservableSettings
}