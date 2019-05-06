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
        interactor.getScheduleDayList(weekStart!!, weekEnd!!, teacherId)
    }

    override fun updateLessonStatus(lesson: Lesson?, lessonStatus: LessonStatus?, force: Boolean) {
        if (lesson == null ||
            lessonStatus == null ||
            lessonStatus.identifier == lesson.lessonStatus?.identifier
        ) return

        if (!force) {
            showWarningDialog.value = Pair(lesson, lessonStatus)
        } else {
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
                lessonsObserver.value = scheduleDayList[currentDate]?.lessons ?: listOf()
            }
            throwable != null -> {
                errorObserver.postValue(throwable.localizedMessage)
            }
        }
    }

    override fun onUpdateLessonStatus(throwable: Throwable?) {
        if (throwable != null) {
            errorObserver.setValue(throwable.localizedMessage)
        } else {
            obtainScheduleDayList(teacherId)
        }

        showWarningDialog.value = null
        cancelWarningDialog.setValue(true)
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

    private val dateChangeObserver = Observer<String> { date ->
        date?.let { stringDate ->
            this.currentDate = stringDate

            val parseDate = CalendarUtil.parseFromString(stringDate)

            if (parseDate.less(weekStart) || parseDate.more(weekEnd)) {
                recalculateWeek(parseDate)
                obtainScheduleDayList(teacherId)
            } else {
                lessonsObserver.value = daysMap?.get(stringDate)?.lessons ?: listOf()
            }
        }
    }
}