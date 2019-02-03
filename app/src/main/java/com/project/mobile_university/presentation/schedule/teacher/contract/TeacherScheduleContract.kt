package com.project.mobile_university.presentation.schedule.teacher.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import java.util.*

interface TeacherScheduleContract {
    enum class State {
        IDLE, INIT
    }

    interface ObservableStorage {
        val errorObserver: LiveData<String>
        val currentDate: MutableLiveData<Date>
        val scheduleDayList: LiveData<List<ScheduleDay>>
        val state: LiveData<State>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainScheduleDayList(startWeekDay: Date,
                                  endWeekDay: Date,
                                  teacherId: Long)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainScheduleDayList(scheduleDayList: List<ScheduleDay>?,
                                    throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getScheduleDayList(startWeekDay: Date,
                               endWeekDay: Date, teacherId: Long)
    }

    interface InputModule {
        fun createFragment(teacherId: Long): Fragment
    }
}