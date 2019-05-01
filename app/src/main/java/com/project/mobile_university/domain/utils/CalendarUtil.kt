package com.project.mobile_university.domain.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {
    enum class DayEnum(
        private val calendarEnum: Int,
        val appEnum: Int
    ) {
        MONDAY(Calendar.MONDAY, 1),
        TUESDAY(Calendar.TUESDAY, 2),
        WEDNESDAY(Calendar.WEDNESDAY, 3),
        THURSDAY(Calendar.THURSDAY, 4),
        FRIDAY(Calendar.FRIDAY, 5),
        SATURDAY(Calendar.SATURDAY, 6),
        SUNDAY(Calendar.SUNDAY, 7);

        companion object {
            fun getAppEnumDay(value: Int): Int {
                return values().find { it.calendarEnum == value }?.appEnum ?: 0
            }
        }
    }

    @SuppressLint("ConstantLocale")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @JvmStatic
    fun convertToSimpleFormat(date: Date): String {
        return simpleDateFormat.format(date)
    }

    fun parseFromString(stringDate: String): Date {
        return simpleDateFormat.parse(stringDate)
    }

    fun obtainMondayAndSunday(date: Date): Pair<Date, Date> {
        val diff = obtainDayOfWeek(date) - DayEnum.MONDAY.appEnum

        return Calendar.getInstance().apply {
            time = date
        }.run {
            this.add(Calendar.DATE, -diff)
            val start = this.time
            this.add(Calendar.DATE, 6)
            val end = this.time
            Pair(start, end)
        }
    }

    fun buildRangeBetweenDates(startWeek: Date, endWeek: Date): List<String> {
        val datesInRange = mutableListOf<String>()

        val startCalendar = Calendar.getInstance(Locale.getDefault()).apply {
            time = startWeek
        }
        val endCalendar = Calendar.getInstance(Locale.getDefault()).apply {
            time = endWeek
        }

        while (startCalendar <= endCalendar) {
            val result = startCalendar.time
            datesInRange.add(convertToSimpleFormat(result))
            startCalendar.add(Calendar.DATE, 1)
        }

        return datesInRange
    }

    fun getDayEnum(date: Date): Int {
        val calendar = Calendar.getInstance().apply { time = date }
        val calendarEnum = calendar.get(Calendar.DAY_OF_WEEK)
        return DayEnum.getAppEnumDay(calendarEnum)
    }

    private fun obtainDayOfWeek(date: Date): Int {
        val calendar = Calendar.getInstance().apply { time = date }
        val calendarEnum = calendar.get(Calendar.DAY_OF_WEEK)
        return DayEnum.getAppEnumDay(calendarEnum)
    }
}