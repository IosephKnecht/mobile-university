package com.project.mobile_university.presentation.schedule.subgroup.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.mobile_university.data.presentation.ScheduleDay as ScheduleDayPresentation
import java.util.*

interface ScheduleContract {
    enum class State {
        IDLE, INIT
    }

    interface ObservableStorage {
        val scheduleDayList: LiveData<List<ScheduleDayPresentation>>
        val currentDate: MutableLiveData<Date>
        val errorObserver: LiveData<String>
    }

    interface Presenter : MvpPresenter,
        ObservableStorage {
        fun obtainLessonList(subgroupId: Long)
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

    interface Router : MvpRouter<RouterListener>

    interface ScheduleInputModuleContract {
        fun createFragment(subgroupId: Long): Fragment
    }
}