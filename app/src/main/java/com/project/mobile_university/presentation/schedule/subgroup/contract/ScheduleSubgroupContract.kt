package com.project.mobile_university.presentation.schedule.subgroup.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ScheduleDay as ScheduleDayPresentation
import java.util.*

interface ScheduleSubgroupContract {
    enum class State {
        IDLE, INIT
    }

    interface ObservableStorage {
        val scheduleDayList: LiveData<List<ScheduleDayPresentation>>
        val errorObserver: LiveData<String>
        val dateObserver: LiveData<Date>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainLessonList(subgroupId: Long)
        fun showLessonInfo(lessonId: Long)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainLessonList(lessonList: List<ScheduleDayPresentation>?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getLessonList(startWeek: Date,
                          endWeek: Date,
                          subgroupId: Long)
    }

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener> {
        fun showLessonInfo(androidComponent: AndroidComponent, lessonId: Long)
    }

    interface InputModule {
        fun createFragment(subgroupId: Long): Fragment
    }
}