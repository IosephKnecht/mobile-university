package com.project.mobile_university.presentation.lessonInfo.teacher.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract

class LessonInfoTeacherPresenter(
    private val lessonExtId: Long,
    private val interactor: LessonInfoTeacherContract.Interactor
) : AbstractPresenter(),
    LessonInfoTeacherContract.Presenter,
    LessonInfoTeacherContract.Listener {

    override val lesson = MutableLiveData<Lesson>()
    override val errorObserver = SingleLiveData<Throwable>()

    override val loadingState = MutableLiveData<Boolean>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}