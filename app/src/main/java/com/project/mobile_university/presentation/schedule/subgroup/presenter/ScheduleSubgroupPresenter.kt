package com.project.mobile_university.presentation.schedule.subgroup.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.less
import com.project.mobile_university.presentation.more
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import java.util.*

class ScheduleSubgroupPresenter(
    private val interactor: ScheduleSubgroupContract.Interactor,
    private val subgroupId: Long,
    private val hostObservableStorage: ScheduleHostContract.ExternalObservableStorage
) : AbstractPresenter(), ScheduleSubgroupContract.Presenter, ScheduleSubgroupContract.Listener {

    override val errorObserver = SingleLiveData<String>()
    override val lessonsObserver = MutableLiveData<List<Lesson>>()

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

        interactor.setListener(this)

        hostObservableStorage.dateChange
            .observe(androidComponent.activityComponent!!, dateChangeObserver)
    }

    override fun detachAndroidComponent() {
        hostObservableStorage.dateChange.removeObserver(dateChangeObserver)
		interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun obtainLessonList(subgroupId: Long) {
        interactor.getLessonList(weekStart!!, weekEnd!!, subgroupId)
    }

    override fun onObtainLessonList(lessonList: Map<String, ScheduleDay>?, throwable: Throwable?) {
        when {
            throwable != null -> {
                errorObserver.postValue(throwable.localizedMessage)
            }
            lessonList != null -> {
                this.daysMap = lessonList
                this.lessonsObserver.value = lessonList[currentDate]?.lessons ?: listOf()
            }
        }
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
                obtainLessonList(subgroupId)
            } else {
                lessonsObserver.value = daysMap?.get(stringDate)?.lessons ?: listOf()
            }
        }
    }
}