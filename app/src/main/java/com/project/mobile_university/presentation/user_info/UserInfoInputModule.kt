package com.project.mobile_university.presentation.user_info

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
import com.project.mobile_university.presentation.user_info.view.UserInfoFragment

class UserInfoInputModule : UserInfoContract.InputModule {
    override fun createFragment(userId: Long): Fragment {
        return UserInfoFragment.createInstance(userId)
    }
}