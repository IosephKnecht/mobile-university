package com.project.mobile_university.presentation.schedule.host.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import java.util.*

interface ScheduleHostContract {
    enum class InitialScreenType {
        SUBGROUP, TEACHER
    }

    enum class CurrentScreenType {
        SUBGROUP, TEACHER, SETTINGS, LESSON_INFO
    }

    interface ExternalObservableStorage {
        val dateChange: LiveData<String>
    }

    interface InternalNavigationStorage {
        val currentScreen: LiveData<CurrentScreenType>
    }

    interface Presenter : MvpPresenter, ExternalObservableStorage, InternalNavigationStorage {
        val identifier: Long
        val initialScreenType: InitialScreenType

        fun onShowSubgroupSchedule(identifier: Long)
        fun onShowTeacherSchedule(identifier: Long)
        fun onShowSettings()
        fun onDateChange(date: Date)
        fun onShowLessonInfo(lessonId: Long)
        fun backPressed()
        fun restoreDefaultDate(): Calendar
    }

    interface Listener : MvpInteractor.Listener

    interface Interactor : MvpInteractor<Listener>

    interface RouterListener : MvpRouter.Listener {
        fun onChangeScreen(currentScreenType: CurrentScreenType)
    }

    interface Router : MvpRouter<RouterListener> {
        fun showSubgroupScreen(androidComponent: AndroidComponent, identifier: Long)
        fun showTeacherScreen(androidComponent: AndroidComponent, identifier: Long)
        fun showSettingsScreen(androidComponent: AndroidComponent)
        fun showLessonInfo(androidComponent: AndroidComponent, lessonId: Long)
        fun onBackPressed(androidComponent: AndroidComponent)
    }

    interface InputModule {
        fun createFragment(identifier: Long, initialScreenType: InitialScreenType): Fragment
    }
}