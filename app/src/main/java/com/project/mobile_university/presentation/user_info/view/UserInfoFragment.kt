package com.project.mobile_university.presentation.user_info.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.user_info.assembly.UserInfoComponent
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract

class UserInfoFragment : AbstractFragment<UserInfoContract.Presenter>() {

    companion object {
        private const val USER_ID_KEY = "user_id"

        fun createInstance(userId: Long) = UserInfoFragment().apply {
            arguments = Bundle().apply {
                putLong(USER_ID_KEY, userId)
            }
        }
    }

    private lateinit var diComponent: UserInfoComponent

    override fun inject() {

        val userId = arguments?.getLong(USER_ID_KEY) ?: throw RuntimeException("user id could not be null")

        diComponent = AppDelegate.presentationComponent
            .userInfoSubComponent()
            .with(this)
            .userId(userId)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }
}