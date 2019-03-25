package com.project.mobile_university.presentation.schedule.subgroup.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Events
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class ScheduleSubgroupPresenter(private val interactor: ScheduleSubgroupContract.Interactor,
                                private val subgroupId: Long)
    : AbstractPresenter(), ScheduleSubgroupContract.Presenter, ScheduleSubgroupContract.Listener {

    override val scheduleDayList = MutableLiveData<List<ScheduleDay>>(arrayListOf())
    override val errorObserver = SingleLiveData<String>()
    override val dateObserver = MutableLiveData<Date>(Date())

    private var state = ScheduleSubgroupContract.State.IDLE

    private lateinit var weekStart: Date
    private lateinit var weekEnd: Date

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        interactor.setListener(this)

        recalculateWeek(dateObserver.value)

        if (state == ScheduleSubgroupContract.State.IDLE) {
            obtainLessonList(subgroupId)
            state = ScheduleSubgroupContract.State.INIT
        }

        EventBus.getDefault().register(this)
    }

    override fun detachAndroidComponent() {
        EventBus.getDefault().unregister(this)

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

    @Subscribe
    fun onDateChangeEvent(event: Events.DateChangeEvent) {
        event.date
            ?.apply { dateObserver.postValue(this) }
            ?.takeIf { it < weekStart || it > weekEnd }
            ?.let {
                recalculateWeek(it)
                obtainLessonList(subgroupId)
            }
    }
}