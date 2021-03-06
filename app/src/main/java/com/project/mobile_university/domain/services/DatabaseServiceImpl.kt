package com.project.mobile_university.domain.services

import com.project.mobile_university.data.room.entity.Lesson
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.entity.ScheduleDay
import com.project.mobile_university.data.room.entity.Subgroup
import com.project.mobile_university.data.room.tuple.ScheduleDayWithLessons
import com.project.mobile_university.domain.UniversityDatabase
import com.project.mobile_university.domain.shared.DatabaseService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class DatabaseServiceImpl(private val database: UniversityDatabase) : DatabaseService {

    override fun saveScheduleDay(
        scheduleDayList: List<ScheduleDay>
    ): Single<List<Long>> {
        return Single.fromCallable {
            val lessons = mutableListOf<Lesson>()
            val subgroups = mutableListOf<Subgroup>()
            val lessonSubgroupRelations = mutableListOf<LessonSubgroup>()

            scheduleDayList.forEach { day ->
                val innerLessons = day.lessons

                innerLessons.forEach { innerLesson ->
                    subgroups.addAll(innerLesson.subgroupList)

                    lessonSubgroupRelations.addAll(innerLesson.subgroupList.map {
                        LessonSubgroup(
                            innerLesson.id,
                            it.id
                        )
                    })
                }

                lessons.addAll(innerLessons)
            }

            database.scheduleDayDao().insertScheduleDay(
                scheduleDays = scheduleDayList.toTypedArray(),
                lessons = lessons.toTypedArray(),
                subgroups = subgroups.toTypedArray(),
                lessonSubgroup = lessonSubgroupRelations.toTypedArray()
            )
        }
    }

    override fun getScheduleDayListForSubgroup(
        datesRange: List<String>,
        subgroupId: Long
    ): Single<List<ScheduleDayWithLessons>> {
        return Single.fromCallable {
            database.scheduleDayDao().getScheduleDayWithLessonsForSubgroup(datesRange, subgroupId)
        }
    }

    override fun getScheduleDayListForTeacher(
        datesRange: List<String>,
        teacherId: Long
    ): Single<List<ScheduleDayWithLessons>> {
        return Single.fromCallable {
            database.scheduleDayDao().getScheduleDayWithLessonsForTeacher(datesRange, teacherId)
        }

    }

    override fun getLessonWithSubgroup(lessonExtId: Long): Single<Lesson> {
        return Single.fromCallable {
            database.scheduleDayDao().getLessonWithSubgroups(lessonExtId)
        }
    }

    override fun saveLessons(lessons: List<Lesson>): Single<List<Long>> {
        return Single.fromCallable {
            val insertSubgroups = mutableListOf<Subgroup>()
            val lessonSubgroupRelations = mutableListOf<LessonSubgroup>()

            lessons.forEach { lesson ->
                insertSubgroups.addAll(lesson.subgroupList)

                lessonSubgroupRelations.addAll(lesson.subgroupList.map { subgroup ->
                    LessonSubgroup(
                        lesson.id,
                        subgroup.id
                    )
                })
            }

            database.scheduleDayDao().insertLessons(
                lessons.toTypedArray(),
                insertSubgroups.toTypedArray(),
                lessonSubgroupRelations.toTypedArray()
            )
        }
    }

    override fun clearAll(): Completable {
        return Completable.fromCallable {
            database.clearAllTables()
        }
    }
}