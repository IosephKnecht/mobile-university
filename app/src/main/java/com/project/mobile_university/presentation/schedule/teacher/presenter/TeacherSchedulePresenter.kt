package com.project.mobile_university.presentation.schedule.teacher.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Events
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract.State
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class TeacherSchedulePresenter(private val interactor: TeacherScheduleContract.Interactor,
                               private val teacherId: Long) : AbstractPresenter(), TeacherScheduleContract.Presenter,
    TeacherScheduleContract.Listener {

    override val errorObserver = MutableLiveData<String>()
    override val scheduleDayList = MutableLiveData<List<ScheduleDay>>()
    override val dateObserver = MutableLiveData<Date>(Date())
    override val state = MutableLiveData(State.IDLE)

    private lateinit var weekStart: Date
    private lateinit var weekEnd: Date

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        recalculateWeek(dateObserver.value)

        if (state.value == State.IDLE) {
            obtainScheduleDayList(teacherId)
            state.value = State.INIT
        }

        EventBus.getDefault().register(this)
    }

    override fun detachAndroidComponent() {
        EventBus.getDefault().unregister(this)

        super.detachAndroidComponent()
    }

    override fun obtainScheduleDayList(teacherId: Long) {
        interactor.getScheduleDayList(weekStart, weekEnd, teacherId)
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

    @Subscribe
    fun onDateChangeEvent(event: Events.DateChangeEvent) {
        event.date
            ?.apply { dateObserver.postValue(this) }
            ?.takeIf { it < weekStart || it > weekEnd }
            ?.let {
                recalculateWeek(it)
                obtainScheduleDayList(teacherId)
            }
    }
}