package com.project.mobile_university.presentation.schedule.subgroup.interactor

import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class ScheduleSubgroupInteractor(private val scheduleRepository: ScheduleRepository,
                                 errorHandler: ExceptionConverter) : InteractorWithErrorHandler<ScheduleSubgroupContract.Listener>(errorHandler),
    ScheduleSubgroupContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getLessonList(startWeek: Date, endWeek: Date, subgroupId: Long) {

        val observable = scheduleRepository.syncScheduleDaysForSubgroup(startWeek, endWeek, subgroupId)

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