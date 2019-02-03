package com.project.mobile_university.presentation.login.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.schedule.CommonScheduleActivity

class LoginRouter
    : AbstractRouter<LoginContract.RouterListener>(), LoginContract.Router {

    override fun showStudentScheduleScreen(androidComponent: AndroidComponent, subgroupId: Long) {
        androidComponent.activityComponent?.let {
            it.startActivity(CommonScheduleActivity.createInstance(it, subgroupId, CommonScheduleActivity.ScheduleEnum.SUBGROUP))
        }
    }

    override fun showTeacherScheduleScreen(androidComponent: AndroidComponent, teacherId: Long) {
        androidComponent.activityComponent?.let {
            it.startActivity(CommonScheduleActivity.createInstance(it, teacherId, CommonScheduleActivity.ScheduleEnum.TEACHER))
        }
    }
}