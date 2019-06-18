package com.project.mobile_university.presentation.user_info.router

import android.content.Intent
import android.net.Uri
import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract

class UserInfoRouter : AbstractRouter<UserInfoContract.RouterListener>(),
    UserInfoContract.Router {

    override fun trySendEmail(androidComponent: AndroidComponent, email: String) {
        androidComponent.activityComponent?.apply {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            }

            emailIntent.resolveActivity(packageManager)?.let {
                startActivity(emailIntent)
            } ?: routerListener?.onFailSendEmail(Throwable(getString(R.string.user_info_fail_send_email_string)))
        }
    }
}