package com.project.mobile_university.domain.services

import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.tuple.LessonWithSubgroups
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.domain.UniversityDatabase
import com.project.mobile_university.domain.shared.DatabaseService
import com.project.mobile_university.domain.utils.database.ScheduleSqlUtil
import io.reactivex.Observable

class DatabaseServiceImpl(private val database: UniversityDatabase) : DatabaseService {
    override fun saveScheduleDay(
        scheduleDayList: List<ScheduleDay>,
        requestedDayIds: List<String>
    ): Observable<Unit> {
        return makeReactive {
            ScheduleSqlUtil.insertOrReplaceScheduleDays(database, requestedDayIds, scheduleDayList)
        }
    }

    override fun getScheduleDayListForSubgroup(
        datesRange: List<String>,
        subgroupId: Long
    ): Observable<List<ScheduleDayWithLessons>> {
        return makeReactive {
            database.sheduleDayDao().getScheduleDayWithLessonsForSubgroup(datesRange, subgroupId)
        }
    }

    override fun getScheduleDayListForTeacher(
        datesRange: List<String>,
        teacherId: Long
    ): Observable<List<ScheduleDayWithLessons>> {
        return makeReactive {
            database.sheduleDayDao().getScheduleDayWithLessonsForTeacher(datesRange, teacherId)
        }

    }

    override fun getLessonWithSubgroup(lessonId: Long): Observable<LessonWithSubgroups> {
        return makeReactive {
            database.lessonDao().getLessonByExtId(lessonId).run {
                database.lessonSubgroupDao().getLessonWithSubgroups(this.id)
            }
        }
    }

    override fun saveLesson(lesson: Lesson): Observable<Unit> {
        return makeReactive {
            ScheduleSqlUtil.insertOrReplaceLesson(database, lesson)
        }
    }

    override fun getLessonByExtId(extId: Long): Observable<Lesson> {
        return makeReactive {
            database.lessonDao().getLessonByExtId(extId)
        }
    }

    override fun deleteRelationsForLesson(lessonId: Long): Observable<Unit> {
        return makeReactive {
            database.lessonSubgroupDao().removeRelationsForLesson(lessonId)
        }
    }

    private fun <T> makeReactive(block: () -> T): Observable<T> {
        return Observable.fromCallable { block() }
    }
}