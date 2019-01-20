package com.project.mobile_university.presentation.schedule.presenter

import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract

class SchedulePresenter : AbstractPresenter(), ScheduleContract.Presenter, ScheduleContract.Listener {
    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}