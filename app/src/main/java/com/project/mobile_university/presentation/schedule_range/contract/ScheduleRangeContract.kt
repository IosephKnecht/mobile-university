package com.project.mobile_university.presentation.schedule_range.contract

import androidx.fragment.app.Fragment
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.router.MvpRouter

interface ScheduleRangeContract {
    interface ObservableStorage {

    }

    interface Presenter : MvpPresenter, ObservableStorage {

    }

    interface Listener : MvpInteractor.Listener {

    }

    interface Interactor : MvpInteractor<Listener> {

    }

    interface RouterListener : MvpRouter.Listener {

    }

    interface Router : MvpRouter<RouterListener> {

    }

    interface InputModule {
        fun createFragment(): Fragment
    }
}