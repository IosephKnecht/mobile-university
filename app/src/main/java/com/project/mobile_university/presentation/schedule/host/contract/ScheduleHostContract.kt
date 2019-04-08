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

    interface ExternalObservableStorage {
        val dateChange: LiveData<Date>
    }

    interface InternalNavigationStorage {
        val toolbarVisible: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ExternalObservableStorage, InternalNavigationStorage {
        val identifier: Long
        val screenType: ScreenType

        fun onShowSubgroupSchedule(identifier: Long)
        fun onShowTeacherSchedule(identifier: Long)
        fun onShowSettings()
        fun onDateChange(date: Date)
        fun onShowLessonInfo(lessonId: Long)
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
        fun showLessonInfo(androidComponent: AndroidComponent, lessonId: Long)
    }

    interface InputModule {
        fun createFragment(identifier: Long, screenType: ScreenType): Fragment
    }
}