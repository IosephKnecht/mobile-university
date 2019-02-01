package com.project.mobile_university.domain.adapters.exception

import com.project.mobile_university.data.exceptions.AppException

interface ExceptionAdapter {
    fun mathToAppException(inputData: Throwable): AppException
}