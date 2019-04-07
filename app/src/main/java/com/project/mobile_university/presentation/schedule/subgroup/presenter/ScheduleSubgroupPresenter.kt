package com.project.mobile_university.presentation.schedule.subgroup.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import java.util.*

class ScheduleSubgroupPresenter(
    private val interactor: ScheduleSubgroupContract.Interactor,
    private val router: ScheduleSubgroupContract.Router,
    private val subgroupId: Long,
    private val hostObservableStorage: ScheduleHostContract.ObservableStorage
) : AbstractPresenter(), ScheduleSubgroupContract.Presenter, ScheduleSubgroupContract.Listener {

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

        hostObservableStorage.dateChange
            .observe(androidComponent.activityComponent!!, dateChangeObserver)
    }

    override fun detachAndroidComponent() {
        hostObservableStorage.dateChange.removeObserver(dateChangeObserver)
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun obtainLessonList(subgroupId: Long) {
        interactor.getLessonList(weekStart, weekEnd, subgroupId)
    }

    override fun showLessonInfo(lessonId: Long) {
        router.showLessonInfo(androidComponent!!, lessonId)
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

    private val dateChangeObserver = Observer<Date> { date ->
        date?.apply { dateObserver.value = this }
            ?.takeIf { date -> date < weekStart || date > weekEnd }
            ?.let { date ->
                recalculateWeek(date)
                obtainLessonList(subgroupId)
            }
    }
}