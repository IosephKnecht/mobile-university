package com.project.mobile_university.presentation.schedule.contract

import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter

interface ScheduleContract {
    interface Presenter : MvpPresenter

    interface Listener : MvpInteractor.Listener

    interface Interactor : MvpInteractor<Listener>

    interface RouterListener : MvpRouter.Listener

    interface Router : MvpRouter<RouterListener>
}