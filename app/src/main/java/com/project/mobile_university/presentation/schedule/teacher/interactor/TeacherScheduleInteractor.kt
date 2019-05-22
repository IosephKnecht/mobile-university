package com.project.mobile_university.presentation.schedule.teacher.interactor

import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.common.InteractorWithErrorHandler
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class TeacherScheduleInteractor(
    private val scheduleRepository: ScheduleRepository,
    exceptionConverter: ExceptionConverter
) :
    InteractorWithErrorHandler<TeacherScheduleContract.Listener>(exceptionConverter),
    TeacherScheduleContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getScheduleDayList(startWeekDay: Date, endWeekDay: Date, teacherId: Long) {
        val observable = scheduleRepository.syncScheduleDaysForTeacher(startWeekDay, endWeekDay, teacherId)
            .map { scheduleDays ->
                val daysMap = mutableMapOf<String, ScheduleDay>()
                scheduleDays.forEach { scheduleDay ->
                    daysMap[scheduleDay.currentDate] = scheduleDay
                }
                daysMap
            }

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

    override fun updateLessonStatus(lessonId: Long, lessonStatus: LessonStatus) {
        val observable = scheduleRepository.updateLessonStatus(lessonId, lessonStatus)

        compositeDisposable.add(simpleDiscardResult(observable) { listener, throwable ->
            listener!!.onUpdateLessonStatus(throwable)
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}