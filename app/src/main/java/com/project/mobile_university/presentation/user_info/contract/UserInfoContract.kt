package com.project.mobile_university.presentation.user_info.contract

import androidx.fragment.app.Fragment
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter

interface UserInfoContract {
    interface ObservableStorage

    interface Presenter : MvpPresenter, ObservableStorage

    interface Listener : MvpInteractor.Listener

    interface Interactor : MvpInteractor<Listener>

    interface InputModule {
        fun createFragment(userId: Long): Fragment
    }
}