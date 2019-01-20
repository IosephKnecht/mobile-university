package com.project.mobile_university.presentation.schedule.contract

import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.mobile_university.data.gson.Lesson
import java.util.*

interface ScheduleContract {
    enum class State {
        IDLE, INIT
    }

    interface ObservableStorage {
        val lessonList: LiveData<List<Lesson>>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainLessonList(currentDate: Date, groupId: Long)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainLessonList(lessonList: List<Lesson>?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getLessonList(currentDate: Date, groupId: Long)
    }

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener>
}