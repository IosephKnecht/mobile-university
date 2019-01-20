package com.project.mobile_university.domain.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    @SuppressLint("ConstantLocale")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())

    @JvmStatic
    fun convertToSimpleFormat(date: Date): String {
        return simpleDateFormat.format(date)
    }
}