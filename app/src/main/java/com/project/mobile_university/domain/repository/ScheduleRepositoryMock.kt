package com.project.mobile_university.domain.repository

import com.project.mobile_university.data.Beans
import com.project.mobile_university.data.presentation.*
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

class ScheduleRepositoryMock : ScheduleRepository {
    override fun syncScheduleDaysForSubgroup(
        startDate: Date,
        endDate: Date,
        subgroupId: Long
    ): Single<List<ScheduleDay>> {
        return Single.fromCallable {
            generateScheduleForSubgroup(startDate, endDate, subgroupId)
        }
    }

    override fun syncScheduleDaysForTeacher(
        startDate: Date,
        endDate: Date,
        teacherId: Long
    ): Single<List<ScheduleDay>> {
        return Single.fromCallable {
            generateScheduleForSubgroup(startDate, endDate, 0L)
        }
    }

    override fun syncSchedule(): Single<List<ScheduleDay>> {
        return Single.fromCallable {
            val (startDate, endDate) = CalendarUtil.obtainMondayAndSunday(Date())
            generateScheduleForSubgroup(startDate, endDate, 0L)
        }
    }

    override fun getLesson(lessonExtId: Long): Single<Lesson> {
        return Single.fromCallable { Beans.getLesson(lessonExtId) }
    }

    override fun updateLessonStatus(lessonId: Long, lessonStatus: LessonStatus): Completable {
        return Completable.complete()
    }

    override fun syncLesson(lessonExtId: Long): Single<Lesson> {
        return Single.error(Throwable())
    }

    override fun getCheckList(checkListExtId: Long): Single<List<CheckListRecord>> {
        return Single.error(Throwable())
    }

    override fun putCheckList(checkListExtId: Long, records: List<CheckListRecord>): Completable {
        return Completable.complete()
    }

    override fun createCheckList(lessonExtId: Long): Single<Lesson> {
        return Single.error(Throwable())
    }

    override fun getTeachers(limit: Int, offset: Int): Single<List<Teacher>> {
        return Single.error(Throwable())
    }

    override fun getUserInfo(userId: Long): Single<UserInformation> {
        return Single.error(Throwable())
    }

    override fun getScheduleDaysForTeacher(
        startDate: Date,
        endDate: Date,
        teacherId: Long,
        limit: Int,
        offset: Int
    ): Single<List<ScheduleDay>> {
        return Single.error(Throwable())
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