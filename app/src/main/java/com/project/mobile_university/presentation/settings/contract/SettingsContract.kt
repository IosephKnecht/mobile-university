package com.project.mobile_university.presentation.settings.contract

import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.mobile_university.data.presentation.UserInfo

interface SettingsContract {
    enum class State {
        IDLE, INIT
    }

    interface ObservableStorage {
        val userInfo: LiveData<UserInfo>
        val throwableObserver: LiveData<String>
        val successLogout: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainUserInfo()
        fun clearCache()
        fun exit()
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainUserInfo(userInfo: UserInfo?, throwable: Throwable?)
        fun onClearCache()
        fun onExit(throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getUserInfo()
        fun logout()
    }

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener>

    interface InputModule
}