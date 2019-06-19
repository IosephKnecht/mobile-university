package com.project.mobile_university.presentation.login

import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.login.view.fragment.LoginFragment

class LoginInputModule : LoginContract.InputModule {
    override fun createFragment(): LoginFragment {
        return LoginFragment.createInstance()
    }
}