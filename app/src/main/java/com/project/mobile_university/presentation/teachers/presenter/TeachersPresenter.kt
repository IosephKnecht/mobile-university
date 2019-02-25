package com.project.mobile_university.presentation.teachers.presenter

import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.teachers.contract.TeachersContract

class TeachersPresenter : AbstractPresenter(), TeachersContract.Presenter {

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
    }
}