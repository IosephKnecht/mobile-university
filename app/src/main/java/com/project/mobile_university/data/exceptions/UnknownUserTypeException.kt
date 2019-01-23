package com.project.mobile_university.data.exceptions

import java.lang.RuntimeException

class UnknownUserTypeException(message: String = "Unknown user type") : RuntimeException(message)