package com.project.mobile_university.presentation.schedule_range.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.presentation.schedule_range.view.adapter.ScheduleDayViewState
import io.reactivex.Single
import java.util.*

interface ScheduleRangeContract {

    interface ObservableStorage {
        val pageProgress: LiveData<Boolean>
        val refreshProgress: LiveData<Boolean>
        val emptyError: LiveData<Boolean>
        val emptyView: LiveData<Boolean>
        val emptyProgress: LiveData<Boolean>
        val errorMessage: LiveData<Throwable>
        val showData: LiveData<List<ScheduleDayViewState>>

        val editLessonInfo: LiveData<Long>
        val showLessonInfo: LiveData<Long>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun loadNewPage()
        fun refreshAllPage()
    }

    interface Listener : MvpInteractor.Listener {
        fun onCheckUser(pair: Pair<Long, Boolean>?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun obtainScheduleList(startDate: Date, endDate: Date, teacherId: Long, page: Int): Single<List<ScheduleDay>>
        fun checkUser(lessonId: Long, teacherId: Long)
    }

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener>

    interface InputModule {
        fun createFragment(startDate: Date, endDate: Date, teacherId: Long): Fragment
    }
}