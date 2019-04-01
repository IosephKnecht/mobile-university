package com.project.mobile_university.presentation.lessonInfo.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.presentation.Lesson

interface LessonInfoContract {
    interface ObservableStorage {
        val lesson: LiveData<Lesson>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainLessonFromCache()
        fun obtainLessonFromOnline()
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainLesson(lesson: Lesson?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getLesson(lessonId: Long, fromCache: Boolean)
    }

    interface InputModule {
        fun createFragment(lessonId: Long): Fragment
    }
}