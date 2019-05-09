package com.project.mobile_university.presentation.schedule.subgroup.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.ScheduleDay as ScheduleDayPresentation
import java.util.*

interface ScheduleSubgroupContract {
    interface ObservableStorage {
        val lessonsObserver: LiveData<List<Lesson>>
        val errorObserver: LiveData<String>

        val emptyState: LiveData<Boolean>
        val loadingState: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainLessonList(subgroupId: Long)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainLessonList(lessonList: Map<String, ScheduleDayPresentation>?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getLessonList(
            startWeek: Date,
            endWeek: Date,
            subgroupId: Long
        )
    }

    interface InputModule {
        fun createFragment(subgroupId: Long): Fragment
    }
}