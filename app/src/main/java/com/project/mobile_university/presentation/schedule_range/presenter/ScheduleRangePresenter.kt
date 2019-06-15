package com.project.mobile_university.presentation.schedule_range.presenter

import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract

class ScheduleRangePresenter(
    private val interactor: ScheduleRangeContract.Interactor,
    private val router: ScheduleRangeContract.Router
) : AbstractPresenter(),
    ScheduleRangeContract.Presenter,
    ScheduleRangeContract.Listener,
    ScheduleRangeContract.RouterListener {

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        interactor.setListener(this)
        router.setListener(this)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()

        interactor.setListener(null)
        router.setListener(null)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}