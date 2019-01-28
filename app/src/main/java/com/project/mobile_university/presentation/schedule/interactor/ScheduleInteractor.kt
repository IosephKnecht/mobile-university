package com.project.mobile_university.presentation.schedule.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.domain.utils.DateUtil
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import java.util.*

class ScheduleInteractor(private val apiService: ApiService,
                         private val databaseService: DatabaseService) : AbstractInteractor<ScheduleContract.Listener>(),
    ScheduleContract.Interactor {

    override fun getLessonList(currentDate: Date, subgroupId: Long) {
        val observable = apiService.getScheduleByDate(currentDate, subgroupId)
            .flatMap { serverResponse ->
                databaseService.saveScheduleDay(serverResponse.objectList!![0], listOf(DateUtil.convertToSimpleFormat(currentDate)))
                    //FIXME: test implementation, please delete me
                    .map { serverResponse }
            }

        discardResult(observable) { listener, result ->
            result.apply {
                when {
                    throwable != null -> {
                        listener!!.onObtainLessonList(null, throwable)
                    }
                    else -> {
                        val objectList = data?.objectList

                        if (objectList != null && objectList.isNotEmpty()) {
                            listener!!.onObtainLessonList(data!!.objectList!![0].lessons, null)
                        } else {
                            listener!!.onObtainLessonList(listOf(), null)
                        }
                    }
                }
            }
        }
    }
}