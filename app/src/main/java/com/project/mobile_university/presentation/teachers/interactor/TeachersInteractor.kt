package com.project.mobile_university.presentation.teachers.interactor

import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.teachers.contract.TeachersContract

class TeachersInteractor(exceptionConverter: ExceptionConverter) :
    InteractorWithErrorHandler<TeachersContract.Listener>(exceptionConverter), TeachersContract.Interactor {

    override fun onDestroy() {
        super.onDestroy()
    }
}