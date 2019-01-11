package com.project.mobile_university.presentation.login.view.dialog

import android.support.v4.app.Fragment
import com.project.mobile_university.data.presentation.ServerConfig
import java.io.Serializable

interface OnChangeServerDialog {
    fun onChangeServerDialog(serverConfig: ServerConfig)

    interface ChangeServerDialogProvider : Serializable {
        fun getListener(fragment: Fragment): OnChangeServerDialog
    }
}