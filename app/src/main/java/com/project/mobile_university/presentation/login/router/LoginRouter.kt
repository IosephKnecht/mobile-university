package com.project.mobile_university.presentation.login.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract

class LoginRouter(private val scheduleHostInputModule: ScheduleHostContract.InputModule) :
    AbstractRouter<LoginContract.RouterListener>(), LoginContract.Router {

    override fun showStudentScheduleScreen(androidComponent: AndroidComponent, subgroupId: Long) {
        androidComponent.fragmentManagerComponent?.apply {
            beginTransaction()
                .replace(
                    R.id.fragment_container, scheduleHostInputModule.createFragment(
                        identifier = subgroupId,
                        screenType = ScheduleHostContract.ScreenType.SUBGROUP
                    )
                )
                .commit()
        }
    }

    override fun showTeacherScheduleScreen(androidComponent: AndroidComponent, teacherId: Long) {
        androidComponent.fragmentManagerComponent?.apply {
            beginTransaction()
                .replace(
                    R.id.fragment_container, scheduleHostInputModule.createFragment(
                        identifier = teacherId,
                        screenType = ScheduleHostContract.ScreenType.TEACHER
                    )
                )
                .commit()
        }
    }
}