package com.project.mobile_university.domain.utils.database

import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.gson.Subgroup as SubgroupGson
import com.project.mobile_university.data.gson.Lesson as LessonGson
import com.project.mobile_university.data.gson.ScheduleDay as ScheduleDayGson
import com.project.mobile_university.domain.UniversityDatabase
import com.project.mobile_university.domain.mappers.LessonMapper
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.domain.mappers.SubgroupMapper

object ScheduleUtils {
    fun insertOrReplaceScheduleDayList(database: UniversityDatabase,
                                       requestedDayIds: List<String>,
                                       dayGsonList: List<ScheduleDayGson>) {
        val scheduleDayDao = database.sheduleDayDao()

        val serverListIds = dayGsonList.map { it.currentDate }
        val storedDays = scheduleDayDao.getScheduleDayList(serverListIds)

        when {
            storedDays.isEmpty() || dayGsonList.isNotEmpty() -> {
                val dayIds = scheduleDayDao
                    .insert(*ScheduleDayMapper.toDatabase(dayGsonList).toTypedArray())

                val scheduleDayWithLessonsMap = createScheduleDayLessonsMap(dayIds, dayGsonList)
                insertOrReplaceLessons(database, scheduleDayWithLessonsMap)
            }
            requestedDayIds.isNotEmpty() && dayGsonList.isEmpty() -> {
                scheduleDayDao.deleteScheduleDayList(requestedDayIds)
            }
        }
    }

    private fun insertOrReplaceLessons(database: UniversityDatabase,
                                       scheduleDayWithLesson: Map<Long, List<LessonGson>>) {
        val lessonDao = database.lessonDao()
        scheduleDayWithLesson.entries.forEach { (scheduleDayId, lessonListGson) ->
            val storedLessonSql = lessonDao.getLessonByScheduleDayId(scheduleDayId)
            when {
                storedLessonSql.isEmpty() || lessonListGson.isNotEmpty() -> {
                    val newLessons = LessonMapper.toDatabase(lessonListGson)
                        .map {
                            it.dayId = scheduleDayId
                            return@map it
                        }

                    val lessonIds = lessonDao.insert(*newLessons.toTypedArray())
                    val lessonWithSubgroups = createLessonSubgroupsMap(lessonIds, lessonListGson)
                    insertOrReplaceSubgroups(database, lessonWithSubgroups)
                }
            }
        }
    }

    private fun insertOrReplaceSubgroups(database: UniversityDatabase,
                                         lessonWithSubgroups: Map<Long, List<SubgroupGson>>) {
        val subgroupDao = database.subgroupDao()
        val lessonSubgroupDao = database.lessonSubgroupDao()
        val lessonSubgroupList = mutableListOf<LessonSubgroup>()
        val insertedSubgroup = mutableSetOf<SubgroupGson>()

        lessonWithSubgroups.entries.forEach { (lessonId, subgroupsGson) ->
            val storedSubgroups = lessonSubgroupDao.getSubgroupIdsByLessonId(lessonId)
            when {
                storedSubgroups.isEmpty() || subgroupsGson.isNotEmpty() -> {
                    val needInsertSubgroupGson = subgroupsGson.minus(insertedSubgroup)

                    val subgroupIds = subgroupDao
                        .insert(*SubgroupMapper.toDatabase(needInsertSubgroupGson).toTypedArray())

                    insertedSubgroup.addAll(subgroupsGson)

                    subgroupIds.forEach { subgroupId ->
                        lessonSubgroupList.add(LessonSubgroup(lessonId, subgroupId))
                    }
                }
            }
        }
        createManyToManyRelation(database, lessonSubgroupList)
    }

    private fun createScheduleDayLessonsMap(dayIds: List<Long>,
                                            dayGsonList: List<ScheduleDayGson>): Map<Long, List<LessonGson>> {
        val scheduleDayLessonsMap = mutableMapOf<Long, List<LessonGson>>()
        for (i in 0..(dayIds.size - 1)) {
            scheduleDayLessonsMap.put(dayIds[i], dayGsonList[i].lessons)
        }

        return scheduleDayLessonsMap
    }

    private fun createLessonSubgroupsMap(lessonIds: List<Long>,
                                         lessonGsonList: List<LessonGson>): Map<Long, List<SubgroupGson>> {
        val lessonSubgroupsMap = mutableMapOf<Long, List<SubgroupGson>>()
        lessonIds.withIndex().forEach { (index, _) ->
            lessonSubgroupsMap.put(lessonIds[index], lessonGsonList[index].subgroupList)
        }
        return lessonSubgroupsMap
    }

    private fun createManyToManyRelation(database: UniversityDatabase,
                                         lessonSubgroups: List<LessonSubgroup>) {
        val lessonSubgroupDao = database.lessonSubgroupDao()
        lessonSubgroupDao.insert(*lessonSubgroups.toTypedArray())
    }
}