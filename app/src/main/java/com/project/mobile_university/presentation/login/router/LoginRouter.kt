package com.project.mobile_university.presentation.login.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.schedule.screen.view.CommonScheduleActivity
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract.ScreenState

class LoginRouter
    : AbstractRouter<LoginContract.RouterListener>(), LoginContract.Router {

    override fun showStudentScheduleScreen(androidComponent: AndroidComponent, subgroupId: Long) {
        androidComponent.activityComponent?.let {
            it.startActivity(CommonScheduleActivity.createInstance(it, subgroupId, ScreenState.SUBGROUP))
        }
    }

    override fun showTeacherScheduleScreen(androidComponent: AndroidComponent, teacherId: Long) {
        androidComponent.activityComponent?.let {
            it.startActivity(CommonScheduleActivity.createInstance(it, teacherId, ScreenState.TEACHER))
        }
    }
}