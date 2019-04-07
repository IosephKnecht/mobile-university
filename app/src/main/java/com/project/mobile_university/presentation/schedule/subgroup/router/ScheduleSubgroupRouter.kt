package com.project.mobile_university.presentation.schedule.subgroup.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract

class ScheduleSubgroupRouter(private val lessonInfoModule: LessonInfoContract.InputModule) :
    AbstractRouter<ScheduleSubgroupContract.RouterListener>(),
    ScheduleSubgroupContract.Router {

    override fun showLessonInfo(androidComponent: AndroidComponent, lessonId: Long) {
        androidComponent.fragmentManagerComponent
            ?.beginTransaction()
            ?.replace(R.id.schedule_fragment_container, lessonInfoModule.createFragment(lessonId))
            ?.addToBackStack(null)
            ?.commit()
    }
}