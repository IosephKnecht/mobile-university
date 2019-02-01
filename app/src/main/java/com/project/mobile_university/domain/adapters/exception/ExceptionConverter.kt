package com.project.mobile_university.domain.adapters.exception

import android.content.Context
import com.project.mobile_university.data.exceptions.AppException
import com.project.mobile_university.data.exceptions.ErrorWithDescription
import com.project.mobile_university.data.exceptions.ErrorWithMessage

class ExceptionConverter(private val context: Context) {

    fun convert(exception: Throwable): String {
        return when (exception) {
            is ErrorWithMessage -> {
                exception.errorMessage
            }
            is ErrorWithDescription -> {
                context.getString(exception.description)
            }
            else -> {
                exception.localizedMessage
            }
        }
    }
}