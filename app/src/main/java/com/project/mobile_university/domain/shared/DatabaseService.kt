package com.project.mobile_university.domain.shared

import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.ScheduleDay
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface DatabaseService {
    fun saveScheduleDay(scheduleDayList: List<ScheduleDay>): Single<List<Long>>

    fun getScheduleDayListForSubgroup(
        datesRange: List<String>,
        subgroupId: Long
    ): Single<List<ScheduleDayWithLessons>>

    fun getScheduleDayListForTeacher(
        datesRange: List<String>,
        teacherId: Long
    ): Single<List<ScheduleDayWithLessons>>

    fun getLessonWithSubgroup(lessonExtId: Long): Single<Lesson>

    fun saveLessons(lessons: List<Lesson>): Single<List<Long>>

    fun clearAll(): Completable
}