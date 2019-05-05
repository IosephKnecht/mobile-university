package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.tuple.LessonWithSubgroups
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import io.reactivex.Observable

interface DatabaseService {
    fun saveScheduleDay(scheduleDayList: List<ScheduleDay>, requestedDayIds: List<String>): Observable<Unit>
    fun getScheduleDayListForSubgroup(
        datesRange: List<String>,
        subgroupId: Long
    ): Observable<List<ScheduleDayWithLessons>>

    fun getScheduleDayListForTeacher(
        datesRange: List<String>,
        teacherId: Long
    ): Observable<List<ScheduleDayWithLessons>>

    fun getLessonWithSubgroup(lessonId: Long): Observable<LessonWithSubgroups>

    fun getLessonByExtId(extId: Long): Observable<Lesson>

    fun saveLesson(lesson: Lesson): Observable<Unit>

    fun deleteRelationsForLesson(lessonId: Long): Observable<Unit>
}