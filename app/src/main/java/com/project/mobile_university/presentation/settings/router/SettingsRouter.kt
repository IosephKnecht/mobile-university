package com.project.mobile_university.presentation.settings.router

import android.content.Intent
import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.MainActivity
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.settings.contract.SettingsContract

class SettingsRouter : AbstractRouter<SettingsContract.RouterListener>(),
    SettingsContract.Router {

    override fun goToAuthScreen(androidComponent: AndroidComponent) {
        androidComponent.activityComponent?.let {
            val intent = MainActivity.createInstance(it.applicationContext)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            it.startActivity(intent)
        }
    }
}