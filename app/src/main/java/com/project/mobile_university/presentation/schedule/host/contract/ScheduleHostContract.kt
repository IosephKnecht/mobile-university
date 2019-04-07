package com.project.mobile_university.presentation.schedule.host.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import java.util.*

interface ScheduleHostContract {
    enum class ScreenType {
        SUBGROUP, TEACHER
    }

    interface ObservableStorage {
        val dateChange: LiveData<Date>
        val toolbarVisible: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        val identifier: Long
        val screenType: ScreenType

        fun obtainSubgroupScreen(identifier: Long)
        fun obtainTeacherScreen(identifier: Long)
        fun obtainSettingsScreen()
        fun onDateChange(date: Date)
    }

    interface Listener : MvpInteractor.Listener

    interface Interactor : MvpInteractor<Listener>

    interface RouterListener : MvpRouter.Listener {
        fun onChangeScreen(toolbarVisible: Boolean)
    }

    interface Router : MvpRouter<RouterListener> {
        fun showSubgroupScreen(androidComponent: AndroidComponent, identifier: Long)
        fun showTeacherScreen(androidComponent: AndroidComponent, identifier: Long)
        fun showSettingsScreen(androidComponent: AndroidComponent)
    }

    interface InputModule {
        fun createFragment(identifier: Long, screenType: ScreenType): Fragment
    }
}