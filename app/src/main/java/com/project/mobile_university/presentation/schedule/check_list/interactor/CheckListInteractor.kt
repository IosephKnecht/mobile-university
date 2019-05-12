package com.project.mobile_university.presentation.schedule.check_list.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.schedule.check_list.contract.CheckListContract

class CheckListInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<CheckListContract.Listener>(),
    CheckListContract.Interactor {
}