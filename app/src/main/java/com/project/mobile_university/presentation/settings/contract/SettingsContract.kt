package com.project.mobile_university.presentation.settings.contract

import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.UserInfo
import com.project.mobile_university.presentation.settings.view.SettingsFragment

interface SettingsContract {
    enum class State {
        IDLE, INIT
    }

    interface ObservableStorage {
        val userInfo: LiveData<UserInfo>
        val throwableObserver: LiveData<String>
        val successClear: LiveData<Boolean>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun obtainUserInfo()
        fun clearCache()
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainUserInfo(userInfo: UserInfo?, throwable: Throwable?)
        fun onClearCache(throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getUserInfo()
        fun clearAllTable()
    }

    interface InputModule {
        fun createFragment(): SettingsFragment
    }
}