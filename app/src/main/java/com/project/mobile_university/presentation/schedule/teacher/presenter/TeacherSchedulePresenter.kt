package com.project.mobile_university.presentation.schedule.teacher.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract.State
import java.util.*

class TeacherSchedulePresenter(private val interactor: TeacherScheduleContract.Interactor,
                               private val teacherId: Long) : AbstractPresenter(), TeacherScheduleContract.Presenter,
    TeacherScheduleContract.Listener {

    override val errorObserver = MutableLiveData<String>()
    override val currentDate = MutableLiveData<Date>(Date())
    override val scheduleDayList = MutableLiveData<List<ScheduleDay>>()
    override val state = MutableLiveData(State.IDLE)

    private lateinit var weekStart: Date
    private lateinit var weekEnd: Date

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        registerObservers(errorObserver, currentDate, scheduleDayList)

        recalculateWeek(currentDate.value)

        if (state.value == State.IDLE) {
            obtainScheduleDayList(weekStart, weekEnd, teacherId)
            state.value = State.INIT
        }

        currentDate.observe(androidComponent) {
            if (it != null && (it < weekStart || it > weekEnd)) {
                recalculateWeek(it)
                obtainScheduleDayList(weekStart, weekEnd, teacherId)
            }
        }
    }

    override fun detachAndroidComponent() {
        unregisterObservers()
        super.detachAndroidComponent()
    }

    override fun obtainScheduleDayList(startWeekDay: Date, endWeekDay: Date, teacherId: Long) {
        interactor.getScheduleDayList(startWeekDay, endWeekDay, teacherId)
    }

    override fun onObtainScheduleDayList(scheduleDayList: List<ScheduleDay>?, throwable: Throwable?) {
        when {
            scheduleDayList != null -> {
                this.scheduleDayList.postValue(scheduleDayList)
            }
            throwable != null -> {
                errorObserver.postValue(throwable.localizedMessage)
            }
        }
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
}