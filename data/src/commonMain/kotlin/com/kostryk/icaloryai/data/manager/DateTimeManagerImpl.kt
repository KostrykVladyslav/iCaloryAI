package com.kostryk.icaloryai.data.manager

import com.kostryk.icaloryai.domain.manager.time.DateTimeManager
import com.kostryk.icaloryai.domain.manager.time.DateTimeManager.Companion.DAYS_IN_YEAR
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DateTimeManagerImpl : DateTimeManager {

    override fun getCurrentDateTime() = getCurrentDateTime(DateTimeManager.DEFAULT_DATE_TIME_FORMAT)

    override fun getCurrentDateTime(format: String): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val localDateTimeFormat = createDateTimeFormat(format)
        return localDateTimeFormat.format(now)
    }

    override fun createLastWeeks(count: Int): MutableList<List<String>> {
        val now = getLastDayOfWeek()

        var currentDayOfYear = now.dayOfYear - 1
        var currentYear = now.year

        val weeks = mutableListOf<List<String>>()
        var days = mutableListOf<String>()
        repeat(count) {
            repeat(DateTimeManager.DAYS_IN_WEEK) {
                if (currentDayOfYear <= 0) {
                    currentYear = currentYear - 1
                    currentDayOfYear = DAYS_IN_YEAR
                }

                val displayDayOfYear = currentDayOfYear.toString().padStart(3, '0')

                days.add(
                    LocalDate.Companion.parse(
                        "$displayDayOfYear-$currentYear",
                        LocalDate.Format {
                            dayOfYear()
                            char('-')
                            year()
                        }).toString()
                )

                currentDayOfYear = currentDayOfYear - 1
            }
            weeks.add(days.reversed())
            days = mutableListOf()
        }
        return weeks
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
    private fun createDateTimeFormat(format: String) =
        LocalDateTime.Format { byUnicodePattern(format) }

    fun getLastDayOfWeek(): LocalDate {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentDayOfYear = now.date.dayOfYear
        val currentYear = now.date.year
        val targetDay = currentDayOfYear - (now.dayOfWeek.ordinal - DateTimeManager.DAYS_IN_WEEK)
        return LocalDate.parse(
            "${targetDay.toString().padStart(3, '0')}-$currentYear",
            LocalDate.Format {
                dayOfYear()
                char('-')
                year()
            })
    }
}