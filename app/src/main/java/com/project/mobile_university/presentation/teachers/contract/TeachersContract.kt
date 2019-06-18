package com.project.mobile_university.presentation.teachers.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.presentation.common.helpers.pagination.Paginator
import com.project.mobile_university.presentation.teachers.view.adapter.SwipeAction
import io.reactivex.Single
import java.util.*

interface TeachersContract {
    interface ObservableStorage {
        val pageProgress: LiveData<Boolean>
        val refreshProgress: LiveData<Boolean>
        val emptyError: LiveData<Boolean>
        val emptyView: LiveData<Boolean>
        val emptyProgress: LiveData<Boolean>
        val errorMessage: LiveData<Throwable>
        val showData: LiveData<List<Teacher>>

        val showProfile: LiveData<Pair<Long, Boolean>>
        val showScheduleRange: LiveData<Triple<Long, Date, Date>>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun loadNewPage()
        fun refreshAllPage()
        fun handleSwipeAction(position: Int, swipeAction: SwipeAction)
    }

    interface Listener : MvpInteractor.Listener {
        fun onCheckUser(data: Pair<Long, Boolean>?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getRequestFactory(page: Int): Single<List<Teacher>>
        fun checkUser(userId: Long)
    }

    interface InputModule {
        fun createFragment(): Fragment
    }
}