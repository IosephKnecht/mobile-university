package com.project.mobile_university.presentation.schedule.check_list.contract

import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter

interface CheckListContract {
    interface ObservableStorage

    interface Presenter : MvpPresenter, ObservableStorage

    interface Listener : MvpInteractor.Listener

    interface Interactor : MvpInteractor<Listener>

    interface InputModule
}