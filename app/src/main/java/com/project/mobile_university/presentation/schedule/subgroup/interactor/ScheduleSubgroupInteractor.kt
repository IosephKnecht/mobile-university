package com.project.mobile_university.presentation.schedule.subgroup.interactor

import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class ScheduleSubgroupInteractor(
    private val scheduleRepository: ScheduleRepository,
    errorHandler: ExceptionConverter
) : InteractorWithErrorHandler<ScheduleSubgroupContract.Listener>(errorHandler),
    ScheduleSubgroupContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getLessonList(startWeek: Date, endWeek: Date, subgroupId: Long) {

        val observable = scheduleRepository.syncScheduleDaysForSubgroup(startWeek, endWeek, subgroupId)
            .map { scheduleDays ->
                val dayWithDate = mutableMapOf<String, ScheduleDay>()
                scheduleDays.forEach { scheduleDay ->
                    dayWithDate[scheduleDay.currentDate] = scheduleDay
                }
                dayWithDate
            }

        compositeDisposable.add(simpleDiscardResult(observable) { listener, result ->
            with(result) {
                when {
                    throwable != null -> {
                        listener?.onObtainLessonList(null, throwable)
                    }
                    data != null -> {
                        listener?.onObtainLessonList(data, null)
                    }
                    else -> {
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