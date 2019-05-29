package com.project.mobile_university.presentation.user_info.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter

interface UserInfoContract {
    interface ObservableStorage {
        val userInfo: LiveData<Any>
        val throwable: LiveData<Throwable>
        val loading: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainUserInfo()
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainUserInfo(user: Any?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getUserInfo(userId: Long)
    }

    interface InputModule {
        fun createFragment(userId: Long): Fragment
    }
}