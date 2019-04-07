package com.project.mobile_university.presentation.schedule.host.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.ScreenType
import java.util.*

class ScheduleHostPresenter(
    override val identifier: Long,
    override val screenType: ScheduleHostContract.ScreenType,
    private val router: ScheduleHostContract.Router
) : AbstractPresenter(), ScheduleHostContract.Presenter, ScheduleHostContract.Listener,
    ScheduleHostContract.RouterListener {

    override val dateChange = MutableLiveData<Date>()
    override val toolbarVisible = MutableLiveData<Boolean>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        router.setListener(this)

        when (screenType) {
            ScreenType.SUBGROUP -> {
                router.showSubgroupScreen(androidComponent, identifier)
            }
            ScreenType.TEACHER -> {
                router.showSubgroupScreen(androidComponent, identifier)
            }
        }
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()

        router.setListener(null)
    }

    override fun onDateChange(date: Date) {
        dateChange.value = date
    }

    override fun obtainSubgroupScreen(identifier: Long) {
        router.showSubgroupScreen(androidComponent!!, identifier)
    }

    override fun obtainTeacherScreen(identifier: Long) {
        router.showTeacherScreen(androidComponent!!, identifier)
    }

    override fun obtainSettingsScreen() {
        router.showSettingsScreen(androidComponent!!)
    }

    override fun onChangeScreen(toolbarVisible: Boolean) {
        this.toolbarVisible.value = toolbarVisible
    }

    override fun onDestroy() {
        // not implemented
    }
}