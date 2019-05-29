package com.project.mobile_university.presentation.user_info.presenter

import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract

class UserInfoPresenter(private val interactor: UserInfoContract.Interactor) : AbstractPresenter(),
    UserInfoContract.Presenter, UserInfoContract.Listener {

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
        interactor.setListener(null)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}