package com.project.mobile_university.presentation.schedule.subgroup.interactor

import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.SerialDisposable
import java.util.*

class ScheduleSubgroupInteractor(
    private val scheduleRepository: ScheduleRepository,
    errorHandler: ExceptionConverter
) : InteractorWithErrorHandler<ScheduleSubgroupContract.Listener>(errorHandler),
    ScheduleSubgroupContract.Interactor {

    private var syncScheduleDayDisposable: Disposable? = null

    override fun getLessonList(startWeek: Date, endWeek: Date, subgroupId: Long) {

        val observable = scheduleRepository.syncScheduleDaysForSubgroup(startWeek, endWeek, subgroupId)
            .map { scheduleDays ->
                val dayWithDate = mutableMapOf<String, ScheduleDay>()
                scheduleDays.forEach { scheduleDay ->
                    dayWithDate[scheduleDay.currentDate] = scheduleDay
                }
                dayWithDate
            }

        syncScheduleDayDisposable?.let { disposable ->
            if (!disposable.isDisposed) disposable.dispose()
        }

        syncScheduleDayDisposable = simpleDiscardResult(observable) { listener, result ->
            listener?.onObtainLessonList(result.data, result.throwable)
        }
    }

    override fun onDestroy() {
        syncScheduleDayDisposable?.dispose()
        super.onDestroy()
    }
}