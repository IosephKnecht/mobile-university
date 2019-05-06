package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.ScheduleDay
import com.project.mobile_university.data.room.tuple.LessonWithSubgroups
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import io.reactivex.Observable

interface DatabaseService {
    fun saveScheduleDay(scheduleDayList: List<ScheduleDay>): Observable<Unit>

    fun getScheduleDayListForSubgroup(
        datesRange: List<String>,
        subgroupId: Long
    ): Observable<List<ScheduleDayWithLessons>>

    fun getScheduleDayListForTeacher(
        datesRange: List<String>,
        teacherId: Long
    ): Observable<List<ScheduleDayWithLessons>>

    fun getLessonWithSubgroup(lessonExtId: Long): Observable<LessonWithSubgroups>

    fun getLessonByExtId(extId: Long): Observable<Lesson>

    fun saveLesson(lesson: Lesson): Observable<Unit>

    fun deleteRelationsForLesson(lessonExtId: Long): Observable<Unit>
}