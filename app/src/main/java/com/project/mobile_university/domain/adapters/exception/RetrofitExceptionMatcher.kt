package com.project.mobile_university.domain.adapters.exception

import com.project.mobile_university.data.exceptions.*
import retrofit2.HttpException
import java.io.IOException

class RetrofitExceptionMatcher : ExceptionAdapter {
    override fun mathToAppException(inputData: Throwable): AppException {
        if (inputData is HttpException) {
            return when (inputData.code()) {
                401 -> ExternalExceptionWithDescription(AppExceptionDict.NOT_AUTHORIZE)
                else -> {
                    ExternalExceptionWithMessage(inputData.code(), inputData.response().message())
                }
            }
        }

        if (inputData is IOException) {
            return ExternalExceptionWithDescription(AppExceptionDict.CONNECTION_FAILED)
        }

        return InternalExceptionWithMessage(inputData.localizedMessage)
    }
}