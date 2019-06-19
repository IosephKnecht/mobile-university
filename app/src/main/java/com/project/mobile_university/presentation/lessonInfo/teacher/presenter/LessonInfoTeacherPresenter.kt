package com.project.mobile_university.presentation.lessonInfo.teacher.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract

class LessonInfoTeacherPresenter(
    private val lessonExtId: Long,
    private val interactor: LessonInfoTeacherContract.Interactor,
    private val router: LessonInfoTeacherContract.Router
) : AbstractPresenter(),
    LessonInfoTeacherContract.Presenter,
    LessonInfoTeacherContract.Listener,
    LessonInfoTeacherContract.RouterListener {

    override val lesson = MutableLiveData<Lesson>()
    override val errorObserver = SingleLiveData<Throwable>()

    override val loadingStateLesson = MutableLiveData<Boolean>()
    override val loadingStateCheckList = MutableLiveData<Boolean>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)

        if (lesson.value == null) {
            obtainLessonFromCache()
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }


    override fun obtainLessonFromCache() {
        loadingStateLesson.value = true
        interactor.getLessonFromCache(lessonExtId)
    }

    override fun obtainLessonFromOnline() {
        loadingStateLesson.value = true
        interactor.getLessonFromOnline(lessonExtId)
    }

    override fun createCheckList() {
        loadingStateCheckList.value = true
        interactor.createCheckList(lessonExtId)
    }

    override fun showLocation() {
        androidComponent?.let {
            val coordinates = lesson.value?.coordinates
            if (coordinates != null && coordinates.size == 2) {
                router.showGeoTag(it, coordinates, 12)
            }
        }
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

        loadingStateLesson.value = false
    }

    override fun onCreateCheckList(lesson: Lesson?, throwable: Throwable?) {
        when {
            lesson != null -> {
                this.lesson.value = lesson
                errorObserver.value = null
            }
            throwable != null -> {
                errorObserver.value = throwable
            }
        }

        loadingStateCheckList.value = false
    }

    override fun onShowGeoTag(throwable: Throwable) {
        this.errorObserver.value = throwable
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}