package com.project.mobile_university.data.exceptions

import androidx.annotation.StringRes

interface ErrorMessage {
    val code: Int
}

interface ErrorWithMessage : ErrorMessage {
    val errorMessage: String
}

interface ErrorWithDescription : ErrorMessage {
    @get:StringRes
    val description: Int
}

sealed class AppException(override val code: Int) : Exception(), ErrorMessage


data class ExternalExceptionWithMessage(override val code: Int,
                                        override val errorMessage: String) : AppException(code), ErrorWithMessage

data class ExternalExceptionWithDescription(override val code: Int,
                                            override val description: Int) : AppException(code), ErrorWithDescription {

    constructor(error: ErrorWithDescription) : this(error.code, error.description)
}

data class InternalExceptionWithMessage(override val errorMessage: String) : AppException(-1), ErrorWithMessage

data class InternalExceptionWithDescription(override val description: Int) : AppException(-1), ErrorWithDescription