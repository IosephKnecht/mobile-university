package com.project.mobile_university.presentation.teachers.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.presentation.common.helpers.Paginator

interface TeachersContract {
    interface ObservableStorage {
        val pageProgress: LiveData<Boolean>
        val refreshProgress: LiveData<Boolean>
        val emptyError: LiveData<Boolean>
        val emptyView: LiveData<Boolean>
        val emptyProgress: LiveData<Boolean>
        val errorMessage: LiveData<Throwable>
        val showData: LiveData<List<Teacher>>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun loadNewPage()
        fun refreshAllPage()
    }

    interface Listener : MvpInteractor.Listener, Paginator.ViewController<Teacher>

    interface Interactor : MvpInteractor<Listener> {
        fun loadPage()
        fun refreshAllPage()
    }

    interface InputModule {
        fun createFragment(): Fragment
    }
}