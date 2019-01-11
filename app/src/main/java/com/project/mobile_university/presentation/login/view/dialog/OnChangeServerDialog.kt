package com.project.mobile_university.presentation.login.view.dialog

import android.support.v4.app.Fragment
import java.io.Serializable

interface OnChangeServerDialog {
    fun onChangeServerDialog()

    interface ChangeServerDialogProvider : Serializable {
        fun getListener(fragment: Fragment): OnChangeServerDialog
    }
}