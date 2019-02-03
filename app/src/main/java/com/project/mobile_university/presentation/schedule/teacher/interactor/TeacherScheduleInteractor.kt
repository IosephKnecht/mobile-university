package com.project.mobile_university.presentation.schedule.teacher.interactor

import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class TeacherScheduleInteractor(private val apiService: ApiService,
                                private val dbService: DatabaseService,
                                exceptionConverter: ExceptionConverter) :
    InteractorWithErrorHandler<TeacherScheduleContract.Listener>(exceptionConverter),
    TeacherScheduleContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getScheduleDayList(startWeekDay: Date, endWeekDay: Date, teacherId: Long) {
        val datesRange = CalendarUtil.buildRangeBetweenDates(startWeekDay, endWeekDay)

        val observable = apiService.getScheduleOfWeekForTeacher(startWeekDay, endWeekDay, teacherId)
            .filter { it.objectList == null }
            .flatMap { serverResponse ->
                dbService
                    .saveScheduleDay(serverResponse.objectList!!, datesRange)
                    .map { serverResponse }
            }

        compositeDisposable.add(simpleDiscardResult(observable) { listener, result ->
            when {
                result.data != null -> {
                    val scheduleDayPresentationList = ScheduleDayMapper.toPresentation(result.data!!.objectList!!)
                    listener!!.onObtainScheduleDayList(scheduleDayPresentationList, null)
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