package com.project.mobile_university.presentation.login.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginRouter : AbstractRouter<LoginContract.RouterListener>(), LoginContract.Router {
    override fun showNextScreen(androidComponent: AndroidComponent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}