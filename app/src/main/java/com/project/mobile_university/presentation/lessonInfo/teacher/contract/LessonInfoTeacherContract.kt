package com.project.mobile_university.presentation.lessonInfo.teacher.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson

interface LessonInfoTeacherContract {
    interface ObservableStorage {
        val lesson: LiveData<Lesson>
        val errorObserver: LiveData<Throwable>

        val loadingStateLesson: LiveData<Boolean>
        val loadingStateCheckList: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainLessonFromCache()
        fun obtainLessonFromOnline()
        fun createCheckList()
        fun showLocation()
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainLesson(lesson: Lesson?, throwable: Throwable?)
        fun onCreateCheckList(lesson: Lesson?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getLessonFromCache(lessonExtId: Long)
        fun getLessonFromOnline(lessonExtId: Long)
        fun createCheckList(lessonExtId: Long)
    }

    interface RouterListener : MvpRouter.Listener {
        fun onShowGeoTag(throwable: Throwable)
    }

    interface Router : MvpRouter<RouterListener> {
        fun showGeoTag(androidComponent: AndroidComponent, coordinates: List<String>, zoom: Int)
    }

    interface InputModule {
        fun createFragment(lessonExtId: Long): Fragment
    }
}