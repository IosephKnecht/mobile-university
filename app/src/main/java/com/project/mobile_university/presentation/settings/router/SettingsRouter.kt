package com.project.mobile_university.presentation.settings.router

import android.content.Intent
import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.MainActivity
import com.project.mobile_university.presentation.settings.contract.SettingsContract

class SettingsRouter : AbstractRouter<SettingsContract.RouterListener>(),
    SettingsContract.Router {

    override fun goToAuthScreen(androidComponent: AndroidComponent) {
        // FIXME: bad solve
        androidComponent.activityComponent?.apply {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("LOGOUT", true)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
        }
    }
}