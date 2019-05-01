package com.project.mobile_university.presentation.schedule.teacher.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import java.util.*

interface TeacherScheduleContract {
    interface ObservableStorage {
        val errorObserver: LiveData<String>
        val lessonsObserver: LiveData<List<Lesson>>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainScheduleDayList(teacherId: Long)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainScheduleDayList(
            scheduleDayList: Map<String, ScheduleDay>?,
            throwable: Throwable?
        )
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getScheduleDayList(
            startWeekDay: Date,
            endWeekDay: Date,
            teacherId: Long
        )
    }

    interface InputModule {
        fun createFragment(teacherId: Long): Fragment
    }
}