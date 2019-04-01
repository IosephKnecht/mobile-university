package com.project.mobile_university.presentation.lessonInfo.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract

class LessonInfoPresenter(private val lessonId: Long,
                          private val interactor: LessonInfoContract.Interactor) : AbstractPresenter(), LessonInfoContract.Presenter,
    LessonInfoContract.Listener {

    override val lesson = MutableLiveData<Lesson>()

    init {
        obtainLessonFromCache()
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun obtainLessonFromCache() {
        interactor.getLesson(lessonId, fromCache = false)
    }

    override fun obtainLessonFromOnline() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onObtainLesson(lesson: Lesson?, throwable: Throwable?) {
        if (throwable == null) {
            this.lesson.value = lesson
        } else {
            // TODO: handle error
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}