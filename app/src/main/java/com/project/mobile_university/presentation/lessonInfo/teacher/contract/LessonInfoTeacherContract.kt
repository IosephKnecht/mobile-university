package com.project.mobile_university.presentation.lessonInfo.teacher.contract

import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.presentation.Lesson

interface LessonInfoTeacherContract {
    interface ObservableStorage {
        val lesson: LiveData<Lesson>
        val errorObserver: LiveData<Throwable>

        val loadingState: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ObservableStorage

    interface Listener : MvpInteractor.Listener

    interface Interactor : MvpInteractor<Listener>

    interface InputModule
}