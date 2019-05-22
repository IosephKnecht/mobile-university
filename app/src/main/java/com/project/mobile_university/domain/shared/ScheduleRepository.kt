package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

interface ScheduleRepository {
    fun syncScheduleDaysForSubgroup(startDate: Date, endDate: Date, subgroupId: Long): Single<List<ScheduleDay>>
    fun syncScheduleDaysForTeacher(startDate: Date, endDate: Date, teacherId: Long): Single<List<ScheduleDay>>
    fun syncSchedule(): Single<List<ScheduleDay>>
    fun getLesson(lessonExtId: Long): Single<Lesson>
    fun syncLesson(lessonExtId: Long): Single<Lesson>
    fun updateLessonStatus(lessonId: Long, lessonStatus: LessonStatus): Completable
    fun getCheckList(checkListExtId: Long): Single<List<CheckListRecord>>
    fun putCheckList(checkListExtId: Long, records: List<CheckListRecord>): Completable
    fun createCheckList(lessonExtId: Long): Single<Lesson>
}