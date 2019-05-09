package com.project.mobile_university.presentation.schedule.teacher.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.less
import com.project.mobile_university.presentation.more
import com.project.mobile_university.presentation.renotify
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import java.util.*

class TeacherSchedulePresenter(
    private val interactor: TeacherScheduleContract.Interactor,
    private val teacherId: Long,
    private val hostObservableStorage: ScheduleHostContract.ExternalObservableStorage
) : AbstractPresenter(), TeacherScheduleContract.Presenter,
    TeacherScheduleContract.Listener {

    override val errorObserver = SingleLiveData<String>()
    override val lessonsObserver = MutableLiveData<List<Lesson>>()
    override val showWarningDialog = MutableLiveData<Pair<Lesson, LessonStatus>>()
    override val cancelWarningDialog = SingleLiveData<Boolean>()

    override val emptyState = MutableLiveData<Boolean>()
    override val loadingState = MutableLiveData<Boolean>()

    @set:Synchronized
    private var weekStart: Date? = null
    @set:Synchronized
    private var weekEnd: Date? = null
    @set:Synchronized
    private var daysMap: Map<String, ScheduleDay>? = null
    @set:Synchronized
    private var currentDate: String? = null


    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        hostObservableStorage.dateChange
            .observe(androidComponent.activityComponent!!, dateChangeObserver)

        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        hostObservableStorage.dateChange.removeObserver(dateChangeObserver)
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun obtainScheduleDayList(teacherId: Long) {
        loadingState.value = true

        interactor.getScheduleDayList(weekStart!!, weekEnd!!, teacherId)
    }

    override fun updateLessonStatus(lesson: Lesson?, lessonStatus: LessonStatus?, force: Boolean) {
        if (lesson == null ||
            lessonStatus == null ||
            lessonStatus.identifier == lesson.lessonStatus?.identifier
        ) {
            lessonsObserver.renotify()
            return
        }

        if (!force) {
            showWarningDialog.value = Pair(lesson, lessonStatus)
        } else {
            lessonsObserver.renotify()
            loadingState.value = true
            interactor.updateLessonStatus(lesson.extId, lessonStatus)
        }
    }

    override fun getLessonByPosition(position: Int): Lesson? {
        return lessonsObserver.value?.get(position)?.run { deepCopy() }
    }

    override fun cancelUpdateLessonStatus() {
        showWarningDialog.value = null
        cancelWarningDialog.setValue(true)
    }

    override fun onObtainScheduleDayList(scheduleDayList: Map<String, ScheduleDay>?, throwable: Throwable?) {
        when {
            scheduleDayList != null -> {
                this.daysMap = scheduleDayList

                setLessonsForToday(scheduleDayList[currentDate]?.lessons)
            }
            throwable != null -> {
                errorObserver.setValue(throwable.localizedMessage)
                setLessonsForToday(listOf())
            }
        }

        loadingState.value = false
    }

    override fun onUpdateLessonStatus(throwable: Throwable?) {
        if (throwable != null) {
            errorObserver.setValue(throwable.localizedMessage)
        } else {
            obtainScheduleDayList(teacherId)
        }

        showWarningDialog.value = null
        cancelWarningDialog.setValue(true)
        loadingState.value = false
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    private fun recalculateWeek(date: Date?) {
        if (date == null) return

        val (monday, sunday) = CalendarUtil.obtainMondayAndSunday(date)
        weekStart = monday
        weekEnd = sunday
    }

    private fun setLessonsForToday(lessonsForToday: List<Lesson>?) {
        if (lessonsForToday != null && lessonsForToday.isNotEmpty()) {
            lessonsObserver.value = lessonsForToday
            emptyState.value = false
        } else {
            emptyState.value = true
        }
    }

    private val dateChangeObserver = Observer<String> { date ->
        date?.let { stringDate ->
            this.currentDate = stringDate

            val parseDate = CalendarUtil.parseFromString(stringDate)

            if (parseDate.less(weekStart) || parseDate.more(weekEnd)) {
                recalculateWeek(parseDate)
                obtainScheduleDayList(teacherId)
            } else {
                setLessonsForToday(daysMap?.get(stringDate)?.lessons)
            }
        }
    }
}