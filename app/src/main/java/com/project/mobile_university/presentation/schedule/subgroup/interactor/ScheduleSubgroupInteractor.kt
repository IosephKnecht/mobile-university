package com.project.mobile_university.presentation.schedule.subgroup.interactor

import com.project.mobile_university.domain.ScheduleService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class ScheduleSubgroupInteractor(private val scheduleService: ScheduleService,
                                 errorHandler: ExceptionConverter) : InteractorWithErrorHandler<ScheduleSubgroupContract.Listener>(errorHandler),
    ScheduleSubgroupContract.Interactor {

    val compositeDisposable = CompositeDisposable()

    override fun getLessonList(startWeek: Date, endWeek: Date, subgroupId: Long) {

        val observable = scheduleService
            .syncScheduleDaysForSubgroup(startWeek, endWeek, subgroupId)
            .map { ScheduleDayMapper.sqlToPresentation(it) }

        compositeDisposable.add(simpleDiscardResult(observable) { listener, result ->
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
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}