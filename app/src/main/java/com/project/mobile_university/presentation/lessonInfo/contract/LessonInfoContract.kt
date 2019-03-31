package com.project.mobile_university.presentation.lessonInfo.contract

import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.presentation.Lesson

interface LessonInfoContract {
    interface ObservableStorage

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainLessonFromCache(lessonId: Long)
        fun obtainLessonFromOnline(lessonId: Long)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainLessonFromCache(lesson: Lesson?, throwable: Throwable?)
        fun onObtainLessonFromOnline(lesson: Lesson?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getLesson(lessonId: Long, fromCache: Boolean)
    }

    interface InputModule
}