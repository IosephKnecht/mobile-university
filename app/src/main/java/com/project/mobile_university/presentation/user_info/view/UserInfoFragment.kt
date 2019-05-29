package com.project.mobile_university.presentation.user_info.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.user_info.assembly.UserInfoComponent
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_user_info.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh_layout?.setOnRefreshListener { presenter.obtainUserInfo() }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            userInfo.observe(viewLifecycleOwner, Observer { userInfo ->
                if (userInfo != null) {
                    // TODO: draw content
                }
            })

            throwable.observe(viewLifecycleOwner, Observer { throwable ->
                if (throwable != null) {
                    context?.let { Toasty.error(it, throwable.localizedMessage).show() }
                }
            })

            loading.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    refresh_layout?.isRefreshing = isLoading
                }
            })
        }
    }
}