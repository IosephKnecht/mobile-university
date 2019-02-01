package com.project.mobile_university.data.exceptions

import com.project.mobile_university.R

enum class AppExceptionDict(override val code: Int,
                            override val description: Int) : ErrorWithDescription {

    NOT_AUTHORIZE(1, R.string.no_authorize_message),
    CONNECTION_FAILED(2, R.string.connection_message),
    UNKNOWN_HTTP_EXCEPTION(3, R.string.unknown_http_message);
}