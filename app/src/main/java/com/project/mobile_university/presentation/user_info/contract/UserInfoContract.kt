package com.project.mobile_university.presentation.user_info.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.UserInformation
import com.project.mobile_university.presentation.user_info.view.adapter.UserContactViewState

interface UserInfoContract {
    interface ObservableStorage {
        val userInfo: LiveData<UserInformation>
        val throwable: LiveData<Throwable>
        val loading: LiveData<Boolean>
        val userContacts: LiveData<List<UserContactViewState>>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainUserInfo()
        fun sendEmail(email: String)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainUserInfo(user: UserInformation?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getUserInfo(userId: Long)
    }

    interface RouterListener : MvpRouter.Listener {
        fun onFailSendEmail(throwable: Throwable)
    }

    interface Router : MvpRouter<RouterListener> {
        fun trySendEmail(androidComponent: AndroidComponent, email: String)
    }

    interface InputModule {
        fun createFragment(userId: Long, isMe: Boolean): Fragment
    }
}