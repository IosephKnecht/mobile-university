package com.project.mobile_university.presentation.schedule.screen.contract

import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent

interface CommonScheduleContract {
    enum class ScreenState {
        SUBGROUP, TEACHER, SETTINGS
    }

    interface ObservableStorage {
        val currentScreenState: LiveData<ScreenState>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainSubgroupScreen(identifier: Long)
        fun obtainTeacherScreen(identifier: Long)
        fun obtainSettingsScreen()
    }

    interface RouterListener : MvpRouter.Listener {
        fun onChangeScreen(state: ScreenState)
    }

    interface Router : MvpRouter<RouterListener> {
        fun showSubgroupScreen(androidComponent: AndroidComponent,
                               identifier: Long)

        fun showTeacherScreen(androidComponent: AndroidComponent,
                              identifier: Long)

        fun showSettingsScreen(androidComponent: AndroidComponent)
    }
}