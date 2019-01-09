package com.project.mobile_university.presentation.login.contract

import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter

interface LoginContract {
    interface Presenter : MvpPresenter

    interface Listener : MvpInteractor.Listener

    interface Interactor : MvpInteractor<Listener>
}