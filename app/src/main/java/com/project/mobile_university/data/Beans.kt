package com.project.mobile_university.data

import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.data.presentation.Subgroup
import com.project.mobile_university.domain.utils.CalendarUtil
import java.util.*

object Beans {
    private val lessonTime = listOf(
        "08:30" to "10:00",
        "10:10" to "11:40",
        "11:50" to "13:20",
        "14:00" to "15:30"
    )

    private val lectureHallNames = listOf("E323", "E523", "E319")

    private val lectureTypeNames = listOf("lecture", "practice")

    private val subjects = listOf(
        "Math", "Biology", "Data science"
    )

    private val dates = listOf(
        "2019-04-01", "2019-04-02", "2019-04-03",
        "2019-04-04", "2019-04-05", "2019-04-06"
    )

    private val teachers = listOf(
        1L to "test_teacher_1",
        2L to "test_teacher_2",
        3L to "test_teacher_3",
        4L to "test_teacher_4"
    )

    private val subgroups = listOf(
        Subgroup(
            extId = 1,
            humanValue = 10,
            name = "15-IS-2(B)"
        ),
        Subgroup(
            extId = 2L,
            humanValue = 10,
            name = "15-IS-2(A)"
        ),
        Subgroup(
            extId = 3L,
            humanValue = 9,
            name = "15-ID-3"
        )
    )

    private val lessonsMonday = listOf(
        Lesson(
            id = 1L,
            extId = 1L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[0].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[0],
            lessonStart = lessonTime[0].first,
            lessonEnd = lessonTime[0].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 2L,
            extId = 2L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[1].second,
            subjectName = subjects[1],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[1],
            lessonStart = lessonTime[1].first,
            lessonEnd = lessonTime[1].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 3L,
            extId = 3L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[1].second,
            subjectName = subjects[2],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[2],
            lessonStart = lessonTime[2].first,
            lessonEnd = lessonTime[2].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        )

    )

    private val lessonsTuesday = listOf(
        Lesson(
            id = 4L,
            extId = 4L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[3].second,
            subjectName = subjects[1],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[1],
            lessonStart = lessonTime[0].first,
            lessonEnd = lessonTime[0].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 5L,
            extId = 5L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[0].second,
            subjectName = subjects[1],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[0],
            lessonStart = lessonTime[1].first,
            lessonEnd = lessonTime[1].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        )
    )

    private val lessonsWensday = listOf(
        Lesson(
            id = 7L,
            extId = 7L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[2].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[2],
            lessonStart = lessonTime[0].first,
            lessonEnd = lessonTime[0].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 8L,
            extId = 8L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[3].second,
            subjectName = subjects[2],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[1],
            lessonStart = lessonTime[1].first,
            lessonEnd = lessonTime[1].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 9L,
            extId = 9L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[1].second,
            subjectName = subjects[1],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[0],
            lessonStart = lessonTime[2].first,
            lessonEnd = lessonTime[2].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        )
    )

    private val lessonsThursDay = listOf(
        Lesson(
            id = 10L,
            extId = 10L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[0].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[2],
            lessonStart = lessonTime[0].first,
            lessonEnd = lessonTime[0].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        )
    )

    private val lessonsFriday = listOf(
        Lesson(
            id = 13L,
            extId = 13L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[3].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[2],
            lessonStart = lessonTime[0].first,
            lessonEnd = lessonTime[0].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 15,
            extId = 15L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[1].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[2],
            lessonStart = lessonTime[1].first,
            lessonEnd = lessonTime[1].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        )
    )

    private val lessonsSaturday = listOf(
        Lesson(
            id = 16L,
            extId = 16L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[0].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[1],
            lessonStart = lessonTime[0].first,
            lessonEnd = lessonTime[0].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 17L,
            extId = 17L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[2].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[2],
            lessonStart = lessonTime[1].first,
            lessonEnd = lessonTime[1].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        ),
        Lesson(
            id = 18L,
            extId = 18L,
            dayId = -1L,
            teacherExtId = teachers[0].first,
            currentDate = dates[0],
            teacherName = teachers[3].second,
            subjectName = subjects[0],
            lectureTypeName = lectureTypeNames[0],
            lectureHallName = lectureHallNames[0],
            lessonStart = lessonTime[2].first,
            lessonEnd = lessonTime[2].second,
            subgroupList = subgroups,
            lessonStatus = LessonStatus.READY.identifier
        )
    )

    private val days = listOf(
        ScheduleDay(
            extId = 1L,
            currentDate = dates[0],
            lessons = lessonsMonday
        ),
        ScheduleDay(
            extId = 2L,
            currentDate = dates[1],
            lessons = lessonsTuesday
        ),
        ScheduleDay(
            extId = 3L,
            currentDate = dates[2],
            lessons = lessonsWensday
        ),
        ScheduleDay(
            extId = 4L,
            currentDate = dates[3],
            lessons = lessonsThursDay
        ),
        ScheduleDay(
            extId = 5L,
            currentDate = dates[4],
            lessons = lessonsFriday
        ),
        ScheduleDay(
            extId = 6L,
            currentDate = dates[5],
            lessons = lessonsSaturday
        )
    )

    fun generateScheduleDay(date: Date): ScheduleDay? {
        return try {
            val ordinal = CalendarUtil.getDayEnum(date)
            val findDay = days[ordinal - 1]
            findDay.rewriteDate(date)
            findDay
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    fun getLesson(lessonId: Long): Lesson {
        val mondayLesson = lessonsMonday.find { it.extId == lessonId }
        if (mondayLesson != null) return mondayLesson

        val lessonTuesday = lessonsTuesday.find { it.extId == lessonId }
        if (lessonTuesday != null) return lessonTuesday

        val lessonWensday = lessonsWensday.find { it.extId == lessonId }
        if (lessonWensday != null) return lessonWensday

        val lessonThursDay = lessonsThursDay.find { it.extId == lessonId }
        if (lessonThursDay != null) return lessonThursDay

        val lessonFriday = lessonsFriday.find { it.extId == lessonId }
        if (lessonFriday != null) return lessonFriday

        val lessonSaturday = lessonsSaturday.find { it.extId == lessonId }
        if (lessonSaturday != null) return lessonSaturday

        throw RuntimeException()
    }

    private fun ScheduleDay.rewriteDate(date: Date) {
        days.apply {
            val dateString = CalendarUtil.convertToSimpleFormat(date)
            currentDate = dateString
            lessons.forEach { lesson ->
                lesson.currentDate = dateString
            }
        }
    }
}