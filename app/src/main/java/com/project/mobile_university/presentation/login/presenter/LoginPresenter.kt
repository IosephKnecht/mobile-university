package com.project.mobile_university.presentation.login.presenter

import android.app.Application
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginPresenter(application: Application) : AbstractPresenter(application), LoginContract.Presenter {

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
    }

    override fun onDestroy() {

    }
}