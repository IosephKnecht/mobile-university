package com.project.mobile_university.presentation.user_info.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract

class UserInfoInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<UserInfoContract.Listener>(),
    UserInfoContract.Interactor {
}