package com.project.mobile_university.presentation.schedule.host.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.InitialScreenType
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.CurrentScreenType
import java.util.*

class ScheduleHostPresenter(
    override val identifier: Long,
    override val initialScreenType: ScheduleHostContract.InitialScreenType,
    private val router: ScheduleHostContract.Router
) : AbstractPresenter(), ScheduleHostContract.Presenter, ScheduleHostContract.Listener,
    ScheduleHostContract.RouterListener {

    override val dateChange = MutableLiveData<String>()
    override val currentScreen = MutableLiveData<CurrentScreenType>()

    init {
        dateChange.value = CalendarUtil.convertToSimpleFormat(Date())
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        router.setListener(this)

        if (currentScreen.value == null) {
            when (initialScreenType) {
                InitialScreenType.SUBGROUP -> {
                    router.showSubgroupScreen(androidComponent, identifier)
                }
                InitialScreenType.TEACHER -> {
                    router.showTeacherScreen(androidComponent, identifier)
                }
            }
        }
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()

        router.setListener(null)
    }

    override fun onDateChange(date: Date) {
        dateChange.value = CalendarUtil.convertToSimpleFormat(date)
    }

    override fun onShowSubgroupSchedule(identifier: Long) {
        router.showSubgroupScreen(androidComponent!!, identifier)
    }

    override fun onShowTeacherSchedule(identifier: Long) {
        router.showTeacherScreen(androidComponent!!, identifier)
    }

    override fun onShowSettings() {
        router.showSettingsScreen(androidComponent!!)
    }

    override fun onChangeScreen(currentScreenType: CurrentScreenType) {
        this.currentScreen.value = currentScreenType
    }

    override fun onShowLessonInfo(lessonExtId: Long) {
        when (initialScreenType) {
            InitialScreenType.SUBGROUP -> {
                router.showLessonInfoStudent(androidComponent!!, lessonExtId)
            }
            InitialScreenType.TEACHER -> {
                router.showLessonInfoTeacher(androidComponent!!, lessonExtId)
            }
        }
    }

    override fun onShowCheckList(checkListExtId: Long) {
        router.showCheckList(androidComponent!!, checkListExtId)
    }

    override fun onShowTeachersScreen() {
        router.showTeachersScreen(androidComponent!!)
    }

    override fun backPressed() {
        router.onBackPressed(androidComponent!!)
    }

    override fun restoreDefaultDate(): Calendar {
        return Calendar.getInstance().apply {
            time = dateChange.value?.run {
                CalendarUtil.parseFromString(this)
            } ?: Date()
        }
    }

    override fun onDestroy() {
        // not implemented
    }
}