package com.kostryk.icaloryai.domain.manager.time

interface DateTimeManager {

    fun getCurrentDateTime(): String

    fun formatDateTime(dateTime: String, format: String, targetFormat: String): String

    companion object {
        const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
        const val DEFAULT_TIME_FORMAT = "HH:mm"
    }
}