package com.project.mobile_university.presentation.teachers.presenter

import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.teachers.contract.TeachersContract

class TeachersPresenter(private val interactor: TeachersContract.Interactor) : AbstractPresenter(),
    TeachersContract.Presenter,
    TeachersContract.Listener {

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}