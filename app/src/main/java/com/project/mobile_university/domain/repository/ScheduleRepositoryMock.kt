package com.project.mobile_university.domain.repository

import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.data.presentation.Subgroup
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Observable
import java.util.*

class ScheduleRepositoryMock : ScheduleRepository {
    override fun syncScheduleDaysForSubgroup(startDate: Date, endDate: Date, subgroupId: Long): Observable<List<ScheduleDay>> {
        return Observable.fromCallable {
            generateScheduleForSubgroup(startDate, endDate, subgroupId)
        }
    }

    override fun syncScheduleDaysForTeacher(startDate: Date, endDate: Date, teacherId: Long): Observable<List<ScheduleDay>> {
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

    override fun getLesson(lessonId: Long): Observable<Lesson> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun generateScheduleForSubgroup(startDate: Date, endDate: Date, subgroupId: Long): List<ScheduleDay> {
        var current = startDate.clone() as Date
        val fakeDays = mutableListOf<ScheduleDay>()


        while (current.before(endDate)) {
            val calendar = Calendar.getInstance()
            calendar.time = current
            calendar.add(Calendar.DATE, 1)
            fakeDays.add(generateDayForSubgroup(current, subgroupId))
            current = calendar.time
        }

        return fakeDays
    }

    private fun generateDayForSubgroup(date: Date, subgroupId: Long): ScheduleDay {
        return ScheduleDay(currentDate = CalendarUtil.convertToSimpleFormat(date),
            extId = 1,
            lessons = listOf(Lesson(extId = 1,
                currentDate = CalendarUtil.convertToSimpleFormat(date),
                teacherName = "test_teacher",
                subjectName = "test_subject",
                lessonStart = "",
                lessonEnd = "",
                lectureTypeName = "",
                lectureHallName = "",
                dayId = 1,
                teacherExtId = 1,
                subgroupList = listOf(Subgroup(
                    extId = 1,
                    name = "test_subgroup",
                    humanValue = 30
                ))
            ))
        )
    }
}