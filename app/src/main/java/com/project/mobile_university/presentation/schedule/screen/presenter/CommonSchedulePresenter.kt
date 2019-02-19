package com.project.mobile_university.presentation.schedule.screen.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract.ScreenState

class CommonSchedulePresenter(private val screenState: ScreenState,
                              private val identifier: Long,
                              private val router: CommonScheduleContract.Router)
    : AbstractPresenter(), CommonScheduleContract.Presenter, CommonScheduleContract.RouterListener {

    override val currentScreenState = MutableLiveData<ScreenState>()


    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        registerObservers(currentScreenState)

        router.setListener(this)

        if (currentScreenState.value == null) {
            when (screenState) {
                ScreenState.SUBGROUP -> obtainSubgroupScreen(identifier)
                ScreenState.TEACHER -> obtainTeacherScreen(identifier)
                ScreenState.SETTINGS -> obtainSettingsScreen()
            }
        }
    }

    override fun detachAndroidComponent() {
        unregisterObservers()
        router.setListener(null)
        super.detachAndroidComponent()
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

    override fun onChangeScreen(state: ScreenState) {
        currentScreenState.postValue(state)
    }

    override fun onDestroy() {
        // not used
    }
}