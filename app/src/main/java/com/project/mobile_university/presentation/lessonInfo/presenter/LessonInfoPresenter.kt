package com.project.mobile_university.presentation.lessonInfo.presenter

import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract

class LessonInfoPresenter(private val lessonId: Long,
                          private val interactor: LessonInfoContract.Interactor) : AbstractPresenter(), LessonInfoContract.Presenter {

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
    }

    override fun obtainLessonFromCache(lessonId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun obtainLessonFromOnline(lessonId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}