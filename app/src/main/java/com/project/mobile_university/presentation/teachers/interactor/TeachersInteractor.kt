package com.project.mobile_university.presentation.teachers.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.teachers.contract.TeachersContract

class TeachersInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<TeachersContract.Listener>(),
    TeachersContract.Interactor {

}