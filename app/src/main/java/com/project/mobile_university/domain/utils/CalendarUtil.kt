package com.project.mobile_university.domain.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {
    @SuppressLint("ConstantLocale")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @JvmStatic
    fun convertToSimpleFormat(date: Date): String {
        return simpleDateFormat.format(date)
    }

    fun obtainMondayAndSunday(date: Date): Pair<Date, Date> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.time = date
        val i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.firstDayOfWeek
        calendar.add(Calendar.DATE, if (i != 0) -i - 7 else 0)
        val start = calendar.time
        calendar.add(Calendar.DATE, 6)
        val end = calendar.time

        return Pair(start, end)
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
}