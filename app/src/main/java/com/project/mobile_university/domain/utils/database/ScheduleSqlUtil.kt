package com.project.mobile_university.domain.utils.database

import com.project.mobile_university.data.room.shared.AbstractDao
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.shared.AbstractEntity
import com.project.mobile_university.domain.UniversityDatabase
import com.project.mobile_university.domain.mappers.ScheduleDayMapper
import com.project.mobile_university.data.gson.ScheduleDay as ScheduleDayGson
import com.project.mobile_university.data.room.entity.Lesson as LessonSql
import com.project.mobile_university.data.room.entity.ScheduleDay as ScheduleDaySql
import com.project.mobile_university.data.room.entity.Subgroup as SubgroupSql

object ScheduleSqlUtil {

    fun insertOrReplaceScheduleDays(database: UniversityDatabase,
                                    requestDayIds: List<String>,
                                    dayGsonList: List<ScheduleDayGson>) {
        val scheduleDayDao = database.sheduleDayDao()
        val storedDays = scheduleDayDao.getScheduleDayList(requestDayIds)

        when {
            storedDays.isEmpty() -> {
                val scheduleDaySqlList = ScheduleDayMapper.toDatabase(dayGsonList)
                insertOrReplaceScheduleDay(database, scheduleDaySqlList)
            }
            storedDays.isNotEmpty() && dayGsonList.isNotEmpty() -> {
                val scheduleDaySqlList = ScheduleDayMapper.toDatabase(dayGsonList)
                val diff = storedDays.minus(scheduleDaySqlList)

                if (diff.isEmpty()) {
                    insertOrReplaceScheduleDay(database, scheduleDaySqlList)
                } else {
                    insertOrReplaceScheduleDay(database, scheduleDaySqlList.minus(diff))
                    scheduleDayDao.delete(*diff.toTypedArray())
                }
            }
            dayGsonList.isEmpty() && storedDays.isNotEmpty() -> {
                scheduleDayDao.deleteScheduleDayList(requestDayIds)
            }
        }
    }

    private fun insertOrReplaceScheduleDay(database: UniversityDatabase,
                                           scheduleDaySqlList: List<ScheduleDaySql>) {
        insertAndReassignIds(scheduleDaySqlList, database.sheduleDayDao())

        insertOrReplaceLessons(database, scheduleDaySqlList)
    }

    private fun insertOrReplaceLessons(database: UniversityDatabase,
                                       scheduleDaySqlList: List<ScheduleDaySql>) {
        val lessonDao = database.lessonDao()
        val lessonsForInsert = mutableListOf<LessonSql>()

        scheduleDaySqlList.forEach { scheduleDay ->
            scheduleDay.lessons.forEach { lesson ->
                lesson.dayId = scheduleDay.id
            }

            lessonsForInsert.addAll(scheduleDay.lessons)
        }

        insertAndReassignIds(lessonsForInsert, lessonDao)
        insertSubgroupList(database, lessonsForInsert)
    }

    private fun insertSubgroupList(database: UniversityDatabase,
                                   lessonSqlList: List<LessonSql>) {
        val subgroupDao = database.subgroupDao()
        val subgroupListSql = mutableListOf<SubgroupSql>()
        val lessonSubgroupList = mutableListOf<LessonSubgroup>()

        lessonSqlList.forEach { lesson ->
            lesson.subgroupList.forEach { subgroup ->
                val lessonSubgroup = LessonSubgroup(lesson.id, subgroup.extId)

                if (!lessonSubgroupList.contains(lessonSubgroup)) {
                    lessonSubgroupList.add(lessonSubgroup)
                }

                if (!subgroupListSql.contains(subgroup)) {
                    subgroupListSql.add(subgroup)
                }
            }
        }

        insertAndReassignIds(subgroupListSql, subgroupDao)

        createManyToManyRelation(database, lessonSubgroupList, subgroupListSql)
    }

    private fun createManyToManyRelation(database: UniversityDatabase,
                                         lessonSubgroupList: List<LessonSubgroup>,
                                         subgroupSqlList: List<SubgroupSql>) {
        val lessonSubgroupDao = database.lessonSubgroupDao()

        lessonSubgroupList.forEach { lessonSubgroup ->
            subgroupSqlList.forEach subgroups@{ subgroup ->
                if (subgroup.extId == lessonSubgroup.subgroupId) {
                    lessonSubgroup.subgroupId = subgroup.id
                    return@subgroups
                }
            }
        }

        lessonSubgroupDao.insert(*lessonSubgroupList.toTypedArray())
    }

    private inline fun <reified T : AbstractEntity> insertAndReassignIds(insertList: List<T>, dao: AbstractDao<T>) {
        val elementIds = dao.insert(*insertList.toTypedArray())
        val size = elementIds.size - 1

        for (i in 0..size) {
            insertList[i].id = elementIds[i]
        }
    }
}