package com.project.mobile_university.presentation.schedule.host.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.gson.User
import java.util.*

interface ScheduleHostContract {
    enum class InitialScreenType {
        SUBGROUP, TEACHER
    }

    enum class CurrentScreenType {
        SUBGROUP,
        TEACHER,
        SETTINGS,
        LESSON_INFO,
        CHECK_LIST,
        TEACHERS_SCREEN,
        USER_INFO,
        SCHEDULE_RANGE
    }

    interface ExternalObservableStorage {
        val dateChange: LiveData<String>
    }

    interface InternalNavigationStorage {
        val currentScreen: LiveData<CurrentScreenType>
        val networkState: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ExternalObservableStorage, InternalNavigationStorage {
        val identifier: Long
        val initialScreenType: InitialScreenType

        fun showProfile()

        fun onShowSubgroupSchedule(identifier: Long)
        fun onShowTeacherSchedule(identifier: Long)
        fun onShowSettings()
        fun onDateChange(date: Date)
        fun onShowLessonInfo(lessonExtId: Long)
        fun onEditLessonInfo(lessonExtId: Long)
        fun onShowCheckList(checkListExtId: Long)
        fun onShowTeachersScreen()
        fun onShowUserInfo(userId: Long, isMe: Boolean)
        fun onShowScheduleRange(teacherId: Long, startDate: Date, endDate: Date)
        fun logout()
        fun backPressed()
        fun restoreDefaultDate(): Calendar
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainUserProfile(userProfile: User?, throwable: Throwable?)
        fun onLogout(throwable: Throwable?)
        fun onNetworkStateChanged(isConnected: Boolean)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun obtainUserProfile()
        fun logout()
    }

    interface RouterListener : MvpRouter.Listener {
        fun onChangeScreen(currentScreenType: CurrentScreenType)
    }

    interface Router : MvpRouter<RouterListener> {
        fun showSubgroupScreen(androidComponent: AndroidComponent, identifier: Long)
        fun showTeacherScreen(androidComponent: AndroidComponent, identifier: Long)
        fun showSettingsScreen(androidComponent: AndroidComponent)
        fun showLessonInfoStudent(androidComponent: AndroidComponent, lessonExtId: Long)
        fun showLessonInfoTeacher(androidComponent: AndroidComponent, lessonExtId: Long)
        fun showCheckList(androidComponent: AndroidComponent, checkListExtId: Long)
        fun showTeachersScreen(androidComponent: AndroidComponent, needPopBackStack: InitialScreenType?)

        fun showUserInfo(
            androidComponent: AndroidComponent,
            userId: Long,
            isMe: Boolean,
            needPopBackStack: InitialScreenType? = null
        )

        fun showScheduleRange(androidComponent: AndroidComponent, teacherId: Long, startDate: Date, endDate: Date)
        fun logout(androidComponent: AndroidComponent)
        fun onBackPressed(androidComponent: AndroidComponent)
    }

    interface InputModule {
        fun createFragment(identifier: Long, initialScreenType: InitialScreenType): Fragment
    }
}