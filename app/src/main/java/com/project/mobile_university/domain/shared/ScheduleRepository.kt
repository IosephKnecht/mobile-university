package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import io.reactivex.Observable
import java.util.*

interface ScheduleRepository {
    fun syncScheduleDaysForSubgroup(startDate: Date, endDate: Date, subgroupId: Long): Observable<List<ScheduleDay>>
    fun syncScheduleDaysForTeacher(startDate: Date, endDate: Date, teacherId: Long): Observable<List<ScheduleDay>>
    fun syncSchedule(): Observable<List<ScheduleDay>>
    fun getLesson(lessonExtId: Long): Observable<Lesson>
    fun syncLesson(lessonExtId: Long): Observable<Lesson>
    fun updateLessonStatus(lessonId: Long, lessonStatus: LessonStatus): Observable<Unit>
}