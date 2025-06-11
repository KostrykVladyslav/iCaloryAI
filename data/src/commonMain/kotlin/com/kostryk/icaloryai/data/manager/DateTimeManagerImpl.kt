package com.kostryk.icaloryai.data.manager

import com.kostryk.icaloryai.domain.manager.time.DateTimeManager
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

class DateTimeManagerImpl : DateTimeManager {

    override fun getCurrentDateTime(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val localDateTimeFormat = createDateTimeFormat(DateTimeManager.DEFAULT_DATE_TIME_FORMAT)
        return localDateTimeFormat.format(now)
    }

    override fun formatDateTime(
        dateTime: String,
        format: String,
        targetFormat: String
    ): String {
        val localDateTimeFormat = createDateTimeFormat(format)
        val dateTime: LocalDateTime = localDateTimeFormat.parse(dateTime)
        return dateTime.format(createDateTimeFormat(targetFormat))
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun createDateTimeFormat(format: String): DateTimeFormat<LocalDateTime> {
        return LocalDateTime.Format {
            byUnicodePattern(format)
        }
    }
}