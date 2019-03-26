package com.project.mobile_university.presentation.schedule.teacher.interactor

import com.project.mobile_university.domain.services.ScheduleService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class TeacherScheduleInteractor(private val scheduleService: ScheduleService,
                                exceptionConverter: ExceptionConverter) :
    InteractorWithErrorHandler<TeacherScheduleContract.Listener>(exceptionConverter),
    TeacherScheduleContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getScheduleDayList(startWeekDay: Date, endWeekDay: Date, teacherId: Long) {
        val observable = scheduleService.syncScheduleDaysForTeacher(startWeekDay, endWeekDay, teacherId)
            .map { ScheduleDayMapper.toPresentation(it) }

        compositeDisposable.add(simpleDiscardResult(observable) { listener, result ->
            when {
                result.data != null -> {
                    listener!!.onObtainScheduleDayList(result.data, null)
                }
                result.throwable != null -> {
                    listener!!.onObtainScheduleDayList(null, result.throwable)
                }
            }
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}