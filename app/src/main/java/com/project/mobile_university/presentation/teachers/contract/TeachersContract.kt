package com.project.mobile_university.presentation.teachers.contract

import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.PaginatedList
import com.project.mobile_university.data.gson.Teacher

interface TeachersContract {
    interface ObservableStorage {

    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainTeachers(page: Int)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainTeachers(paginatedList: PaginatedList<Teacher>?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getTeacherList()
    }
}