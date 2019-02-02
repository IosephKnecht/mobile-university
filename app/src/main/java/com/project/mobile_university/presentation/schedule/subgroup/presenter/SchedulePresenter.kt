package com.project.mobile_university.presentation.schedule.subgroup.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleContract
import java.util.*

class SchedulePresenter(private val interactor: ScheduleContract.Interactor,
                        private val subgroupId: Long)
    : AbstractPresenter(), ScheduleContract.Presenter, ScheduleContract.Listener {

    override val scheduleDayList = MutableLiveData<List<ScheduleDay>>(arrayListOf())
    override val currentDate = MutableLiveData<Date>(Date())
    override val errorObserver = SingleLiveData<String>()

    private var state = ScheduleContract.State.IDLE

    private lateinit var weekStart: Date
    private lateinit var weekEnd: Date

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        registerObservers(scheduleDayList)

        interactor.setListener(this)

        recalculateWeek(currentDate.value)

        if (state == ScheduleContract.State.IDLE) {
            obtainLessonList(subgroupId)
            state = ScheduleContract.State.INIT
        }

        currentDate.observe(androidComponent) {
            if (it != null && (it < weekStart || it > weekEnd)) {
                recalculateWeek(it)
                obtainLessonList(subgroupId)
            }
        }
    }

    override fun detachAndroidComponent() {
        unregisterObservers()
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun obtainLessonList(subgroupId: Long) {
        interactor.getLessonList(weekStart, weekEnd, subgroupId)
    }

    override fun onObtainLessonList(lessonList: List<ScheduleDay>?, throwable: Throwable?) {
        when {
            throwable != null -> {
                errorObserver.postValue(throwable.localizedMessage)
            }
            lessonList != null -> {
                this.scheduleDayList.postValue(lessonList)
            }
        }
    }

    private fun recalculateWeek(date: Date?) {
        if (date == null) return

        val (monday, sunday) = CalendarUtil.obtainMondayAndSunday(date)
        weekStart = monday
        weekEnd = sunday
    }
}