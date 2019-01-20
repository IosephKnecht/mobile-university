package com.project.mobile_university.presentation.schedule.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import java.util.*

class ScheduleInteractor(private val apiService: ApiService) : AbstractInteractor<ScheduleContract.Listener>(),
    ScheduleContract.Interactor {

    override fun getLessonList(currentDate: Date, groupId: Long) {
        val observable = apiService.getScheduleByDate(currentDate, groupId)

        discardResult(observable) { listener, result ->
            result.apply {
                when {
                    throwable != null -> {
                        listener!!.onObtainLessonList(null, throwable)
                    }
                    else -> {
                        listener!!.onObtainLessonList(data!!.objectList!![0][0].lessons, null)
                    }
                }
            }
        }
    }
}