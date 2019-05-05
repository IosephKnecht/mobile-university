package com.project.mobile_university.presentation.lessonInfo.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract

class LessonInfoPresenter(
    private val lessonExtId: Long,
    private val interactor: LessonInfoContract.Interactor
) : AbstractPresenter(), LessonInfoContract.Presenter,
    LessonInfoContract.Listener {

    override val lesson = MutableLiveData<Lesson>()
    override val errorObserver = SingleLiveData<Throwable>()

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
        interactor.getLessonFromCache(lessonExtId)
    }

    override fun obtainLessonFromOnline() {
        interactor.getLessonFromOnline(lessonExtId)
    }

    override fun onObtainLesson(lesson: Lesson?, throwable: Throwable?) {
        when {
            lesson != null -> {
                this.lesson.value = lesson
            }
            throwable != null -> {
                errorObserver.setValue(throwable)
            }
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}