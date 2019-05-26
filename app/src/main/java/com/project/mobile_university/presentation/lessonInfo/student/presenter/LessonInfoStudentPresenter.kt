package com.project.mobile_university.presentation.lessonInfo.student.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract

class LessonInfoStudentPresenter(
    private val lessonExtId: Long,
    private val interactor: LessonInfoStudentContract.Interactor
) : AbstractPresenter(), LessonInfoStudentContract.Presenter,
    LessonInfoStudentContract.Listener {

    override val lesson = MutableLiveData<Lesson>()
    override val errorObserver = SingleLiveData<Throwable>()

    override val loadingState = MutableLiveData<Boolean>()

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
        loadingState.value = true
        interactor.getLessonFromCache(lessonExtId)
    }

    override fun obtainLessonFromOnline() {
        loadingState.value = true
        interactor.getLessonFromOnline(lessonExtId)
    }

    override fun onObtainLesson(lesson: Lesson?, throwable: Throwable?) {
        when {
            lesson != null -> {
                this.lesson.value = lesson
                errorObserver.setValue(null)
            }
            throwable != null -> {
                errorObserver.setValue(throwable)
            }
        }

        loadingState.value = false
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}