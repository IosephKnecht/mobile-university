package com.project.mobile_university.presentation.schedule.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import java.util.*

class ScheduleInteractor(private val apiService: ApiService,
                         private val databaseService: DatabaseService) : AbstractInteractor<ScheduleContract.Listener>(),
    ScheduleContract.Interactor {

    override fun getLessonList(startWeek: Date, endWeek: Date, subgroupId: Long) {
        val datesRange = CalendarUtil.buildRangeBetweenDates(startWeek, endWeek)

        val observable = apiService.getScheduleOfWeek(startWeek, endWeek, subgroupId)
            .filter { it.objectList != null }
            .flatMap { serverResponse ->
                databaseService.saveScheduleDay(serverResponse.objectList!!, datesRange)
                    //FIXME: test implementation, please delete me
                    .map { serverResponse }
            }
            .map { ScheduleDayMapper.toPresentation(it.objectList!!) }

        discardResult(observable) { listener, result ->
            result.apply {
                when {
                    throwable != null -> {
                        listener!!.onObtainLessonList(null, throwable)
                    }
                    else -> {
                        if (data != null) {
                            listener!!.onObtainLessonList(data, null)
                        } else {
                            listener!!.onObtainLessonList(listOf(), null)
                        }
                    }
                }
            }
        }
    }
}