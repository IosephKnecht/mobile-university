package com.project.mobile_university.domain.repository

import com.project.mobile_university.data.Beans
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Observable
import java.util.*

class ScheduleRepositoryMock : ScheduleRepository {
    override fun syncScheduleDaysForSubgroup(
        startDate: Date,
        endDate: Date,
        subgroupId: Long
    ): Observable<List<ScheduleDay>> {
        return Observable.fromCallable {
            generateScheduleForSubgroup(startDate, endDate, subgroupId)
        }
    }

    override fun syncScheduleDaysForTeacher(
        startDate: Date,
        endDate: Date,
        teacherId: Long
    ): Observable<List<ScheduleDay>> {
        return Observable.fromCallable {
            generateScheduleForSubgroup(startDate, endDate, 0L)
        }
    }

    override fun syncSchedule(): Observable<List<ScheduleDay>> {
        return Observable.fromCallable {
            val (startDate, endDate) = CalendarUtil.obtainMondayAndSunday(Date())
            generateScheduleForSubgroup(startDate, endDate, 0L)
        }
    }

    override fun getLesson(lessonExtId: Long): Observable<Lesson> {
        return Observable.fromCallable { Beans.getLesson(lessonExtId) }
    }

    override fun updateLessonStatus(lessonId: Long, lessonStatus: LessonStatus): Observable<Unit> {
        return Observable.create { emitter ->
            emitter.onNext(Unit)
        }
    }

    override fun syncLesson(lessonExtId: Long): Observable<Lesson> {
        return Observable.error(Throwable())
    }

    override fun getCheckList(checkListExtId: Long): Observable<List<CheckListRecord>> {
        return Observable.error(Throwable())
    }

    override fun putCheckList(records: List<CheckListRecord>): Observable<Unit> {
        return Observable.error(Throwable())
    }

    override fun createCheckList(lessonExtId: Long): Observable<Lesson> {
        return Observable.error(Throwable())
    }

    private fun generateScheduleForSubgroup(startDate: Date, endDate: Date, subgroupId: Long): List<ScheduleDay> {
        var current = startDate.clone() as Date
        val fakeDays = mutableListOf<ScheduleDay>()


        while (current.before(endDate)) {
            val calendar = Calendar.getInstance()
            calendar.time = current
            calendar.add(Calendar.DATE, 1)
            Beans.generateScheduleDay(current)?.let { day -> fakeDays.add(day) }
            current = calendar.time
        }

        return fakeDays
    }
}